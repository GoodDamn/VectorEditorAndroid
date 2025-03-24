package good.damn.editor.vector

import android.Manifest
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.anchors.VEMProjectionAnchor
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.editmodes.VEEditModeAnimation
import good.damn.editor.vector.browsers.VEBrowserContent
import good.damn.editor.vector.browsers.interfaces.VEListenerOnGetBrowserContent
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.editor.editmodes.VEEditModeShape
import good.damn.editor.editmodes.VEEditModeSwap
import good.damn.editor.editmodes.VEEditModeTransform
import good.damn.editor.editmodes.freemove.VEEditModeExistingPoint
import good.damn.editor.editmodes.freemove.VEEditModeExistingShape
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectPoint
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectShape
import good.damn.editor.editmodes.listeners.VEIListenerOnTransform
import good.damn.editor.export.VEExport
import good.damn.editor.importer.VEImport3
import good.damn.editor.vector.bottomsheets.VEBottomSheetMakeFill
import good.damn.editor.vector.fragments.adapter.VEFragmentAdapter
import good.damn.editor.vector.fragments.VEFragmentVectorAnimation
import good.damn.editor.vector.fragments.VEFragmentVectorOptions
import good.damn.editor.vector.importer.VEImportAnimationMutable
import good.damn.editor.vector.launchers.VELauncherPermission
import good.damn.editor.vector.launchers.VEListenerOnResultPermission
import good.damn.editor.vector.view.VEViewPaint
import good.damn.editor.views.VEViewVectorEditor
import good.damn.sav.core.VEMProjection
import good.damn.sav.core.animation.animators.VEIListenerAnimationUpdateFrame
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase
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
VEIListenerAnimationUpdateFrame,
VEIListenerOnTransform {

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


    private val mProjection = VEMProjection(
        scale = 1.0f,
        radiusPoint = 50f,
        radiusPointsScaled = 50f
    )

    private val mProjectionAnchor = VEMProjectionAnchor(
        50f,
        15f,
        35f,
        35f,
        15f,
        15f
    )

    private val mAnchor = VEAnchor(
        mProjectionAnchor
    ).apply {
        onAnchorPoint = this@VEActivityMain
    }

    private val mCanvasSize = Size(
        VEApp.width.toFloat(),
        VEApp.width.toFloat()
    )

    private val modeShape = VEEditModeShape(
        mProjection,
        mAnchor,
        mCanvasSize.width,
        mCanvasSize.height
    ).apply {
        onSelectShape = this@VEActivityMain
    }


    private val modeAnimation = VEEditModeAnimation(
        modeShape.shapes,
        modeShape.skeleton,
        mAnchor
    )

    private val modeTransform = VEEditModeTransform().apply {
        transformListener = this@VEActivityMain
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

    private var mRoot: FrameLayout? = null

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
            mRoot = this
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

            VEViewPaint(
                context
            ).apply {

                setOnClickListener {
                    onClickBtnFill(this)
                }

                addView(
                    this,
                    s,
                    s,
                )
            }

            Button(
                context
            ).apply {

                text = "T"

                setOnClickListener {
                    onClickBtnTransform()
                }

                addView(
                    this,
                    s,
                    s
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

    override fun onStart() {
        super.onStart()
        Handler(
            Looper.getMainLooper()
        ).postDelayed({
            onGetBrowserContent(
                intent.data
            )
        }, 1000)
    }

    override fun onGetBrowserContent(
        uri: Uri?
    ) {
        uri ?: return
        contentResolver.openInputStream(
            uri
        )?.apply {

            val processer = mFragmentVectorAnimation
                .processer

            val model = VEImport3.animationWrapper(
                mCanvasSize,
                this,
                false,
                VEImportAnimationMutable(
                    mCanvasSize,
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

                groupFill = model.static.groupsFill.first()
                vectorFill = shapes.firstOrNull()?.fill
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

    private inline fun onClickBtnTransform() = mViewVector?.run {
        mode = modeTransform
        scale += 0.1f
        invalidate()
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
                mCanvasSize,
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

    private inline fun onClickBtnFill(
        v: VEViewPaint
    ) {

        val mode = mViewVector?.mode ?: return
        if (mode is VEEditModeAnimation) {
            mFragmentVectorAnimation
                .processer
                .onSelectFill(
                    modeShape.groupFill
                )

            return
        }

        val root = mRoot
            ?: return

        VEBottomSheetMakeFill(
            modeShape.vectorFill,
            mCanvasSize,
            root
        ) {
            modeShape.groupFill.value = it
            modeShape.vectorFill = it
            it?.fillPaint(v.paint)
            mViewVector?.invalidate()
            v.invalidate()
        }.show()
    }

    override fun onScale(
        v: Float
    ) {
        mViewVector?.apply {
            mProjection.scale = v
            if (v > 1.0f) {
                val s = 1.0f - (v - 1.0f) / 6.0f
                mProjection.apply {
                    radiusPoint = 50f * s
                    radiusPointsScaled = mProjection.radiusPoint
                }

                mProjectionAnchor.apply {
                    radiusPointerScaled = mProjection.radiusPointsScaled
                    propLenScaled = propLen * s
                    propMiddlePointLenScaled = propMiddlePointLen * s
                }
            } else {
                mProjection.apply {
                    radiusPoint = 50f
                    radiusPointsScaled = radiusPoint * v
                }
                mProjectionAnchor.apply {
                    radiusPointerScaled = mProjection.radiusPointsScaled
                    propLenScaled = propLen * v
                    propMiddlePointLenScaled = propMiddlePointLen * v
                }
            }

            scale = v
            updateTransformation()
            invalidate()
        }
    }

    override fun onTranslate(
        x: Float,
        y: Float
    ) {
        mViewVector?.apply {
            translateX = x
            translateY = y
            updateTransformation()
            invalidate()
        }
    }


    override fun onSelectShape(
        shape: VEShapeBase
    ) {

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