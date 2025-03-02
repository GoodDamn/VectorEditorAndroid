package good.damn.editor.vector.bottomsheets

import android.content.Context
import android.graphics.PointF
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.bottomsheets.listeners.VEIListenerBottomSheetFill
import good.damn.editor.vector.extensions.views.bounds
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.editor.vector.view.gradient.VECanvasColorSeek
import good.damn.editor.vector.view.gradient.VEViewGradientMaker
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.core.shapes.fill.VEMFillColor
import good.damn.sav.core.shapes.fill.VEMFillGradientLinear
import good.damn.sav.misc.extensions.toInt32

class VEBottomSheetMakeGradient(
    private val toView: ViewGroup,
    private val onConfirmFill: VEIListenerBottomSheetFill<VEMFillGradientLinear>
): VEBottomSheet(
    toView
) {

    private val mColors = arrayListOf(
        VECanvasColorSeek().apply {
            color = 0xffff0000.toInt()
        },
        VECanvasColorSeek().apply {
            color = 0xff00ff00.toInt()
        }
    )

    private var mViewGradMaker: VEViewGradientMaker? = null

    override fun onCreateView(
        context: Context
    ) = FrameLayout(
        context
    ).apply {

        setBackgroundColor(
            0xff000315.toInt()
        )

        val w = VEApp.width * 0.5f

        mViewGradMaker = VEViewGradientMaker(
            context
        ).apply {
            colors = mColors

            addView(
                this,
                w.toInt(),
                -1
            )
        }

        val wb = VEApp.width * 0.08f
        Button(
            context
        ).apply {

            text = "+"

            setOnClickListener {
                mColors.add(
                    VECanvasColorSeek().apply {
                        color = 0xffffffff.toInt()
                    }
                )

                mViewGradMaker?.apply {
                    layoutColorSeekById(
                        mColors.size - 1
                    )
                    colors = mColors
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
                mColors.removeLastOrNull()
                mViewGradMaker?.apply {
                    colors = mColors
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
            height = VEApp.height * 0.2f,
            gravity = Gravity.BOTTOM
        )
    }

}
