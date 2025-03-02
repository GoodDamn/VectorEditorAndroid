package good.damn.editor.vector.bottomsheets

import android.content.Context
import android.graphics.PointF
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.bottomsheets.listeners.VEIListenerBottomSheetFill
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.editor.vector.view.gradient.VECanvasColorSeek
import good.damn.editor.vector.view.gradient.interfaces.VEIListenerOnGradientPosition
import good.damn.editor.vector.view.gradient.interfaces.VEIListenerOnGradientShader
import good.damn.editor.vector.view.gradient.VEViewGradientMaker
import good.damn.editor.vector.view.gradient.VEViewGradientPlacer
import good.damn.editor.vector.view.gradient.interfaces.VEIListenerOnGradientColorSeek
import good.damn.sav.core.shapes.fill.VEMFillGradientLinear
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.toInt32

class VEBottomSheetMakeGradient(
    private val canvasSize: Size,
    private val toView: ViewGroup,
    private val onConfirmFill: VEIListenerBottomSheetFill<VEMFillGradientLinear>
): VEBottomSheet(
    toView
), VEIListenerOnGradientShader, VEIListenerOnGradientPosition, VEIListenerOnGradientColorSeek {

    private val mColorsSeek = arrayListOf(
        VECanvasColorSeek().apply {
            color = 0xffff0000.toInt()
        },
        VECanvasColorSeek().apply {
            color = 0xff00ff00.toInt()
        }
    )

    private var mPointFrom: PointF? = null
    private var mPointTo: PointF? = null

    private var mColors: IntArray? = null
    private var mPositions: FloatArray? = null

    private var mViewGradMaker: VEViewGradientMaker? = null
    private var mViewGradPlacer: VEViewGradientPlacer? = null

    override fun onCreateView(
        context: Context
    ) = FrameLayout(
        context
    ).apply {

        setBackgroundColor(
            0xff000315.toInt()
        )

        val w = VEApp.width * 0.5f
        val wplacer = VEApp.height * 0.2f

        mViewGradMaker = VEViewGradientMaker(
            context
        ).apply {
            colors = mColorsSeek

            onGradientShader = this@VEBottomSheetMakeGradient
            onSelectColor = this@VEBottomSheetMakeGradient

            addView(
                this,
                w.toInt(),
                -1
            )
        }

        mViewGradPlacer = VEViewGradientPlacer(
            context
        ).apply {

            onChangePosition = this@VEBottomSheetMakeGradient

            boundsFrame(
                width = wplacer,
                height = wplacer,
                start = w
            )

            addView(
                this
            )
        }

        val wb = VEApp.width * 0.08f
        Button(
            context
        ).apply {

            text = "+"

            setOnClickListener {
                mColorsSeek.add(
                    VECanvasColorSeek().apply {
                        color = 0xffffffff.toInt()
                    }
                )

                mViewGradMaker?.apply {
                    layoutColorSeekById(
                        mColorsSeek.size - 1
                    )
                    colors = mColorsSeek
                    invalidate()
                }
            }

            boundsFrame(
                width = wb,
                height = wb,
                start = wb,
                gravity = Gravity.BOTTOM
            )

            addView(
                this
            )
        }

        Button(
            context
        ).apply {
            text = "-"

            setOnClickListener {
                mColorsSeek.removeLastOrNull()
                mViewGradMaker?.apply {
                    colors = mColorsSeek
                    invalidate()
                }
            }

            boundsFrame(
                width = wb,
                height = wb,
                gravity = Gravity.BOTTOM
            )

            addView(
                this
            )
        }

        boundsFrame(
            width = VEApp.width.toFloat(),
            height = wplacer,
            gravity = Gravity.BOTTOM
        )
    }

    override fun onGetGradientShader(
        colors: IntArray,
        positions: FloatArray
    ) {
        mViewGradPlacer?.apply {
            changeShader(
                colors,
                positions
            )
            mColors = colors
            mPositions = positions

            confirmFill()

            invalidate()
        }
    }

    override fun onSelectColorSeek(
        index: Int
    ) {
        VEBottomSheetSelectColor(
            toView
        ) {
            it ?: return@VEBottomSheetSelectColor
            mViewGradMaker?.apply {
                updateGradientColorSeek(
                    index,
                    it.color.toInt32()
                )
                invalidate()
            }
        }.show()
    }

    override fun onGetGradientPosition(
        from: PointF,
        to: PointF
    ) {
        mPointFrom = from
        mPointTo = to

        confirmFill()
    }

    private inline fun confirmFill() {
        val f = mPointFrom
            ?: return

        val p = mPointTo
            ?: return

        val colors = mColors
            ?: return

        val positions = mPositions
            ?: return

        val view = mViewGradPlacer
            ?: return

        onConfirmFill.onConfirmFill(
            VEMFillGradientLinear(
                f.x / view.width * canvasSize.width,
                f.y / view.height * canvasSize.height,
                p.x / view.width * canvasSize.width,
                p.y / view.height * canvasSize.height,
                colors,
                positions
            )
        )
    }
}
