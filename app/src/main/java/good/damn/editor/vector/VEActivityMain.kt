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
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.KeyFrames
import androidx.viewpager2.widget.ViewPager2
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.animation.animator.options.canvas.VEIAnimationOptionCanvas
import good.damn.editor.editmodes.VEEditModeAnimation
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
import good.damn.editor.importer.VEImport2
import good.damn.editor.vector.bottomsheets.VEBottomSheetSetupShape
import good.damn.editor.vector.bottomsheets.listeners.VEIListenerBottomSheetFill
import good.damn.editor.vector.fragments.adapter.VEFragmentAdapter
import good.damn.editor.vector.fragments.VEFragmentVectorAnimation
import good.damn.editor.vector.fragments.VEFragmentVectorOptions
import good.damn.editor.vector.importer.VEImportAnimationMutable
import good.damn.editor.vector.launchers.VELauncherPermission
import good.damn.editor.vector.launchers.VEListenerOnResultPermission
import good.damn.editor.views.VEViewVectorEditor
import good.damn.gradient_color_picker.OnPickColorListener
import good.damn.lib.verticalseekbar.interfaces.VSIListenerSeekBarProgress
import good.damn.sav.core.animation.animators.VEAnimatorInterpolation
import good.damn.sav.core.animation.animators.VEIListenerAnimationUpdateFrame
import good.damn.sav.core.animation.keyframe.VEKeyframes
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.core.shapes.fill.VEMFillColor
import good.damn.sav.core.shapes.primitives.VEShapeBezierQuad
import good.damn.sav.core.shapes.primitives.VEShapeLine
import good.damn.sav.misc.Size
import good.damn.sav.misc.interfaces.VEIDrawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VEActivityMain
: AppCompatActivity(),
VEListenerOnGetBrowserContent,
VEIDrawable,
VEIListenerOnAnchorPoint,
VEIListenerOnSelectShape,
VEIListenerOnSelectPoint,
VEListenerOnResultPermission,
VEIListenerAnimationUpdateFrame {

    companion object {
        private val TAG = VEActivityMain::class.simpleName
        private const val FILE_NAME = "export.avs"
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

    private val modeShape = VEEditModeShape(
        mAnchor,
        VEApp.width.toFloat(),
        VEApp.width.toFloat()
    ).apply {
        onSelectShape = this@VEActivityMain
    }

    private val modeAnimation = VEEditModeAnimation(
        modeShape.shapes,
        modeShape.skeleton,
        mAnchor
    )

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
        onUpdateFrameAnimation = this@VEActivityMain
        modeAnimation.onSelectPoint = processer
        modeAnimation.onSelectShape = processer
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

        mLauncherPermission.register(
            this
        )

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
                modeShape.currentPrimitive = VEShapeLine()
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
                modeShape.currentPrimitive = VEShapeBezierQuad()
            }

            root.addView(
                this
            )
        }

        mViewPager = ViewPager2(
            context
        ).apply {

            isUserInputEnabled = false

            offscreenPageLimit = 2

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
            val canvasSize = Size(
                modeShape.canvasWidth,
                modeShape.canvasHeight
            )

            val processer = mFragmentVectorAnimation
                .processer

            val model = VEImport2.animationWrapper(
                canvasSize,
                this,
                false,
                VEImportAnimationMutable(
                    canvasSize,
                    processer.viewAnimatorEditor!!,
                    2000
                )
            )
            close()

            modeShape.apply {
                skeleton.resetSkeleton(
                    model.static.skeleton.points
                )

                shapes.clear()
                shapes.addAll(
                    model.static.shapes
                )
            }

            processer.clearAnimations()

            model.animations?.forEach {
                processer.addAnimation(
                    it.id,
                    it.animation
                )
            }

            mViewVector?.invalidate()
        }

    }

    override fun draw(
        canvas: Canvas
    ) = modeShape.run {

        shapes.forEach {
            it.draw(
                canvas
            )
        }

        skeleton.draw(
            canvas
        )

        draw(
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

    private inline fun onClickExportVector(
        v: View
    ) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            if (mFileExport == null) {
                mLauncherPermission.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                return
            }
        } else {
            mFileExport = VEFile(
                FILE_NAME
            )
        }

        mFileExport?.apply {
            VEExport.export(
                modeShape.skeleton,
                modeShape.shapes,
                mFragmentVectorAnimation
                    .processer
                    .exportAnimations(),
                Size(
                    modeShape.canvasWidth,
                    modeShape.canvasHeight
                ),
                this
            )
        }
    }

    private inline fun onClickImportVector(
        v: View
    ) = mBrowserContent.launch()

    private inline fun onClickDeleteAll(
        v: View
    ) {
        modeShape.clearActions()
        mViewVector?.invalidate()
    }

    private inline fun onClickUndoAction(
        v: View
    ) {
        modeShape.undoAction()
        mViewVector?.invalidate()
    }

    private inline fun onClickBtnPrev(
        v: View
    ) {
        mViewPager?.currentItem = 0
        mViewVector?.mode = modeShape
        mCurrentAnchor = modeShape
    }

    private inline fun onClickBtnNext(
        v: View
    ) {
        mViewPager?.currentItem = 1
        mViewVector?.mode = modeAnimation
        mCurrentAnchor = modeAnimation
    }

    override fun onSelectShape(
        shape: VEShapeBase
    ) {
        val view = window.decorView
            as? ViewGroup ?: return

        VEBottomSheetSetupShape(
            view,
            shape.fill,
            shape.points.firstOrNull(),
            shape.points.lastOrNull()
        ) {
            shape.fill = it
            modeShape.shapes.forEach { shape ->
                shape.updateFillPaint()
            }
            mViewVector?.invalidate()
        }.apply {
            onSeekProgressWidth = VSIListenerSeekBarProgress {
                shape.strokeWidth = it * modeShape.canvasWidth
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
                    FILE_NAME
                )
            }
        }
    }

    override suspend fun onUpdateFrameAnimation() {
        withContext(
            Dispatchers.Main
        ) {
            mViewVector?.invalidate()
        }
    }
}