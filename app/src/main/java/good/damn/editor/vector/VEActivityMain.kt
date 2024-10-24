package good.damn.editor.vector

import android.app.Activity
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.vector.browsers.VEBrowserContent
import good.damn.editor.vector.browsers.interfaces.VEListenerOnGetBrowserContent
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.editor.options.VEOptionFreeMove
import good.damn.editor.options.VEOptionShapeable
import good.damn.editor.views.VEViewVector
import good.damn.gradient_color_picker.GradientColorPicker
import good.damn.lib.verticalseekbar.VSViewSeekBarV
import good.damn.lib.verticalseekbar.interfaces.VSIListenerSeekBarProgress
import good.damn.sav.core.shapes.VEShapeLine
import good.damn.sav.misc.interfaces.VEIDrawable

class VEActivityMain
: AppCompatActivity(),
VEListenerOnGetBrowserContent,
VSIListenerSeekBarProgress,
VEIDrawable,
VEIListenerOnAnchorPoint {

    private var mViewVector: VEViewVector? = null

    private val mBrowserContent = VEBrowserContent().apply {
        onGetContent = this@VEActivityMain
    }

    private val mAnchor = VEAnchor(
        50f
    ).apply {
        onAnchorPoint = this@VEActivityMain
    }

    private val mOptionShape = VEOptionShapeable(
        mAnchor,
        VEApp.width.toFloat(),
        VEApp.width.toFloat()
    )

    private val mOptionFreeMove = VEOptionFreeMove(
        mAnchor,
        mOptionShape.skeleton
    )

    private var mCurrentAnchor: VEIListenerOnAnchorPoint? = null

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
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

        mCurrentAnchor = mOptionShape

        mViewVector = VEViewVector(
            context,
            mOptionShape
        ).apply {

            setBackgroundColor(
                0xff565656.toInt()
            )

            boundsFrame(
                width = mOptionShape.canvasWidth,
                height = mOptionShape.canvasHeight
            )

            canvasRenderer = this@VEActivityMain

            root.addView(
                this
            )
        }


        Button(
            context
        ).apply {
            text = "SHP"

            val s = VEApp.width * 0.15f

            setOnClickListener {
                mViewVector?.optionable = mOptionShape
                mCurrentAnchor = mOptionShape
            }

            boundsFrame(
                gravity = Gravity.END,
                width = s,
            )

            root.addView(
                this
            )
        }

        Button(
            context
        ).apply {
            text = "MOV"

            val s = VEApp.width * 0.15f

            setOnClickListener {
                mViewVector?.optionable = mOptionFreeMove
                mCurrentAnchor = mOptionFreeMove
            }

            boundsFrame(
                gravity = Gravity.END,
                width = s,
                top = s
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
                mOptionShape.currentPrimitive = VEShapeLine(
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
                mOptionShape.currentPrimitive = good.damn.sav.core.shapes.VEShapeBezierС(
                    0f, 0f
                )
            }

            root.addView(
                this
            )
        }

        VSViewSeekBarV(
            context
        ).apply {

            boundsFrame(
                gravity = Gravity.END,
                height = VEApp.height * 0.2f,
                top = VEApp.width * 0.42f,
                width = VEApp.width * 0.1f
            )

            onSeekProgress = this@VEActivityMain

            strokeWidth = layoutParams.width * 0.25f
            setBackgroundColor(
                0xffff0000.toInt()
            )
            progressColor = 0xffffff00.toInt()

            progress = 0.65f

            root.addView(
                this
            )
        }

        GradientColorPicker(
            context
        ).apply {
            boundsFrame(
                top = VEApp.height * 0.5f,
                width = VEApp.width.toFloat(),
                height = VEApp.height * 0.2f
            )

            setOnPickColorListener {
                mOptionShape
                    .currentPrimitive
                    .color = it

                mViewVector?.invalidate()
            }

            root.addView(
                this
            )
        }

        LinearLayout(
            context
        ).apply {
            orientation = LinearLayout.HORIZONTAL

            Button(
                context
            ).apply {
                text = "Export"
                setOnClickListener(
                    this@VEActivityMain::onClickExportVector
                )
                addView(
                    this
                )
            }

            Button(
                context
            ).apply {
                text = "Import"
                setOnClickListener(
                    this@VEActivityMain::onClickImportVector
                )
                addView(this)
            }

            Button(
                context
            ).apply {
                text = "Delete all"
                setOnClickListener(
                    this@VEActivityMain::onClickDeleteAll
                )
                addView(this)
            }

            Button(
                context
            ).apply {
                text = "Undo"
                setOnClickListener(
                    this@VEActivityMain::onClickUndoAction
                )
                addView(this)
            }

            boundsFrame(
                top = VEApp.height * 0.7f
            )

            root.addView(this)
        }

        setContentView(
            root
        )

    }

    private fun onClickExportVector(
        v: View
    ) = Unit

    private fun onClickImportVector(
        v: View
    ) {
        mBrowserContent.launch()
    }

    private fun onClickDeleteAll(
        v: View
    ) {
        mOptionShape.clearActions()
        mViewVector?.invalidate()
    }

    private fun onClickUndoAction(
        v: View
    ) {
        mOptionShape.undoAction()
        mViewVector?.invalidate()
    }


    override fun onGetBrowserContent(
        uri: Uri?
    ) = Unit

    override fun onSeekProgress(
        progress: Float
    ) {
        mOptionShape.apply {
            currentPrimitive.strokeWidth =
                progress * canvasWidth
        }

        mViewVector?.invalidate()
    }

    override fun onDraw(
        canvas: Canvas
    ) = mOptionShape.run {
        skeleton.onDraw(
            canvas
        )

        shapes.forEach {
            it.onDraw(
                canvas
            )
        }

        mOptionShape.onDraw(
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
}