package good.damn.editor.vector

import android.Manifest
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.animation.animator.options.VEOptionAnimatorBase
import good.damn.editor.animation.animator.options.tickTimer.listeners.VEListenerOnTickColor
import good.damn.editor.editmodes.VEEditModeFillPoints
import good.damn.editor.editmodes.animation.VEEditModeAnimation
import good.damn.editor.vector.browsers.VEBrowserContent
import good.damn.editor.vector.browsers.interfaces.VEListenerOnGetBrowserContent
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.editor.editmodes.VEEditModeShape
import good.damn.editor.editmodes.VEEditModeSwap
import good.damn.editor.editmodes.freemove.VEEditModeExistingPoint
import good.damn.editor.editmodes.freemove.VEEditModeExistingShape
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectPoint
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectShape
import good.damn.editor.export.VEExport
import good.damn.editor.importer.VEImport
import good.damn.editor.vector.bottomsheets.VEBottomSheetSetupShape
import good.damn.editor.vector.fragments.adapter.VEFragmentAdapter
import good.damn.editor.vector.fragments.VEFragmentVectorAnimation
import good.damn.editor.vector.fragments.VEFragmentVectorOptions
import good.damn.editor.vector.launchers.VELauncherPermission
import good.damn.editor.vector.launchers.VEListenerOnResultPermission
import good.damn.editor.views.VEViewVectorEditor
import good.damn.gradient_color_picker.OnPickColorListener
import good.damn.lib.verticalseekbar.interfaces.VSIListenerSeekBarProgress
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.VEShapeBezierС
import good.damn.sav.core.shapes.VEShapeLine
import good.damn.sav.misc.Size
import good.damn.sav.misc.interfaces.VEIDrawable

class VEActivityMain
: AppCompatActivity(),
VEListenerOnGetBrowserContent,
VEIDrawable,
VEIListenerOnAnchorPoint,
VEListenerOnTickColor,
VEIListenerOnSelectShape,
VEIListenerOnSelectPoint, VEListenerOnResultPermission {

    companion object {
        private val TAG = VEActivityMain::class.simpleName
    }

    private var mViewVector: VEViewVectorEditor? = null
    private var mCurrentAnchor: VEIListenerOnAnchorPoint? = null
    private var mViewPager: ViewPager2? = null

    private val mBrowserContent = VEBrowserContent().apply {
        onGetContent = this@VEActivityMain
    }

    private val mAnchor = VEAnchor(
        50f
    ).apply {
        onAnchorPoint = this@VEActivityMain
    }

    private val mFragmentVectorEdit = VEFragmentVectorOptions().apply {
        onClickDeleteAll = View.OnClickListener {
            onClickDeleteAll(it)
        }

        onClickExport = View.OnClickListener {
            onClickExportVector(it)
        }

        onClickImport = View.OnClickListener {
            onClickImportVector(it)
        }

        onClickUndoAction = View.OnClickListener {
            onClickUndoAction(it)
        }

        onClickBtnNext = View.OnClickListener {
            onClickBtnNext(it)
        }
    }

    private val mFragmentVectorAnimation = VEFragmentVectorAnimation().apply {
        onClickBtnPrev = View.OnClickListener {
            onClickBtnPrev(it)
        }

        onTickUpdateAnimation = {
            mViewVector?.invalidate()
        }
    }

    private val modeShape = VEEditModeShape(
        mAnchor,
        VEApp.width.toFloat(),
        VEApp.width.toFloat()
    ).apply {
        onSelectShape = this@VEActivityMain
    }

    private val modeExistingPoint = VEEditModeExistingPoint(
        modeShape.skeleton,
        mAnchor
    )

    private val modeFreeMove = VEEditModeSwap(
        arrayOf(
            modeExistingPoint,
            VEEditModeExistingShape(
                modeShape.shapes
            ).apply {
                onSelectShape = this@VEActivityMain
            }
        )
    )

    private val modeFillPoints = VEEditModeFillPoints(
        modeShape.skeleton
    ).apply {
        onSelectPoint = this@VEActivityMain
    }

    private val modeAnimation = VEEditModeAnimation(
        mAnchor,
        modeShape.skeleton,
        modeShape.shapes
    ).apply {
        onChangeEntityAnimation = mFragmentVectorAnimation
        onChangeValueAnimation = mFragmentVectorAnimation
        editModeAnimShape.onSelectShape = this@VEActivityMain
    }

    private val mLauncherPermission = VELauncherPermission(
        this
    )

    private var mFileExport: VEFile? = null

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            mLauncherPermission.register(
                this
            )
            mLauncherPermission.launch(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }

        mFragmentVectorAnimation.onPlayAnimation = {
            modeAnimation.animatedEntities.run {
                val list = ArrayList<VEOptionAnimatorBase>(size)
                for (pia in values) {
                    pia.options.forEach {
                        list.add(it)
                    }
                }

                list.toTypedArray()
            }
        }

        mBrowserContent.register(
            this
        )

        val context = this

        val root = FrameLayout(
            context
        ).apply {
            setBackgroundColor(
                0xff08193A.toInt()
            )
        }

        mCurrentAnchor = modeShape

        mViewVector = VEViewVectorEditor(
            context,
            modeShape
        ).apply {

            setBackgroundColor(
                0xff565656.toInt()
            )

            boundsFrame(
                width = modeShape.canvasWidth,
                height = modeShape.canvasHeight
            )

            canvasRenderer = this@VEActivityMain

            root.addView(
                this
            )
        }

        LinearLayout(
            context
        ).apply {
            orientation = LinearLayout
                .VERTICAL

            background = null

            val s = (
                VEApp.width * 0.1f
            ).toInt()

            Button(
                context
            ).apply {

                text = "SHP"

                setOnClickListener {
                    mViewVector?.mode = modeShape
                    mCurrentAnchor = modeShape
                }

                addView(
                    this,
                    s, s
                )
            }

            Button(
                context
            ).apply {
                text = "MOV"

                setOnClickListener {
                    mViewVector?.mode = modeFreeMove
                    mCurrentAnchor = modeExistingPoint
                }

                addView(
                    this,
                    s, s
                )
            }

            Button(
                context
            ).apply {

                text = "FIL"

                setOnClickListener {
                    modeShape.apply {
                        shapes.addFirst(
                            modeFillPoints.createShape(
                                canvasWidth,
                                canvasHeight
                            )
                        )
                    }
                    mViewVector?.mode = modeFillPoints
                    mCurrentAnchor = null
                }

                addView(
                    this,
                    s, s
                )
            }


            boundsFrame(
                gravity = Gravity.END,
                width = -2f,
                height = -2f
            )

            root.addView(
                this
            )
        }

        Button(
            context
        ).apply {
            text = "|"

            val s = VEApp.width * 0.1f
            boundsFrame(
                width = s,
                height = s
            )

            setOnClickListener {
                modeShape.currentPrimitive = VEShapeLine(
                    0f, 0f
                )
            }

            root.addView(
                this
            )
        }

        Button(
            context
        ).apply {
            text = "CB"

            val s = VEApp.width * 0.1f
            boundsFrame(
                width = s * 1.5f,
                height = s,
                start = s
            )

            setOnClickListener {
                modeShape.currentPrimitive = VEShapeBezierС(
                    0f, 0f
                )
            }

            root.addView(
                this
            )
        }


        mViewPager = ViewPager2(
            context
        ).apply {

            isUserInputEnabled = false

            adapter = VEFragmentAdapter(
                arrayOf(
                    mFragmentVectorEdit,
                    mFragmentVectorAnimation
                ),
                supportFragmentManager,
                lifecycle
            )

            boundsFrame(
                top = modeShape.canvasHeight,
                width = -1f,
                height = -1f
            )

            root.addView(
                this
            )
        }

        setContentView(
            root
        )
    }


    override fun onGetBrowserContent(
        uri: Uri?
    ) {
        uri ?: return
        contentResolver.openInputStream(
            uri
        )?.apply {
            val model = VEImport.import(
                Size(
                    modeShape.canvasWidth,
                    modeShape.canvasHeight
                ),
                this
            )
            close()

            modeShape.apply {
                skeleton.resetSkeleton(
                    model.skeleton.points
                )

                shapes.clear()
                shapes.addAll(
                    model.shapes
                )

                mViewVector?.invalidate()
            }

        }

    }


    override fun draw(
        canvas: Canvas
    ) = modeShape.run {
        skeleton.draw(
            canvas
        )

        shapes.forEach {
            it.draw(
                canvas
            )
        }

        modeShape.draw(
            canvas
        )
    }

    override fun onAnchorX(
        x: Float
    ) {
        mCurrentAnchor?.onAnchorX(x)
    }

    override fun onAnchorY(
        y: Float
    ) {
        mCurrentAnchor?.onAnchorY(y)
    }

    private fun onClickExportVector(
        v: View
    ) {
        if (mFileExport == null) {
            mLauncherPermission.launch(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            return
        }

        VEExport.export(
            modeShape.skeleton,
            modeShape.shapes,
            Size(
                modeShape.canvasWidth,
                modeShape.canvasHeight
            ),
            mFileExport!!
        )
    }

    private fun onClickImportVector(
        v: View
    ) = mBrowserContent.launch()

    private fun onClickDeleteAll(
        v: View
    ) {
        modeShape.clearActions()
        mViewVector?.invalidate()
    }

    private fun onClickUndoAction(
        v: View
    ) {
        modeShape.undoAction()
        mViewVector?.invalidate()
    }

    private fun onClickBtnPrev(
        v: View
    ) {
        mViewPager?.currentItem = 0
        mViewVector?.mode = modeShape
        mCurrentAnchor = modeShape
    }

    private fun onClickBtnNext(
        v: View
    ) {
        mViewPager?.currentItem = 1
        mViewVector?.mode = modeAnimation
        mCurrentAnchor = modeAnimation.anchor
    }

    override fun onTickColor(
        color: Int
    ) {
        modeShape.currentPrimitive.color = color
        mViewVector?.invalidate()
    }

    override fun onSelectShape(
        shape: VEShapeBase
    ) {
        val view = window.decorView
            as? ViewGroup ?: return

        VEBottomSheetSetupShape(
            view
        ).apply {
            onSeekProgressWidth = object: VSIListenerSeekBarProgress {
                override fun onSeekProgress(
                    progress: Float
                ) {
                    shape.strokeWidth = progress * modeShape.canvasWidth
                    modeAnimation.editModeAnimShape.changeShapeWidth(
                        shape.strokeWidth
                    )
                    mViewVector?.invalidate()
                }
            }
            onPickColor = OnPickColorListener {
                shape.color = it
                modeAnimation.editModeAnimShape.changeShapeColor(
                    it
                )
                mViewVector?.invalidate()
            }
            show()
        }
    }

    override fun onSelectPoint(
        point: VEPointIndexed
    ) {
        mViewVector?.invalidate()
    }

    override fun onResultPermission(
        permission: String,
        isGranted: Boolean
    ) {
        if (!isGranted) {
            return
        }

        when (permission) {
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> {
                mFileExport = VEFile(
                    "export.avs"
                )
            }
        }
    }
}