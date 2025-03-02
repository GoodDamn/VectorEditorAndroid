package good.damn.editor.vector.bottomsheets

import android.content.Context
import android.graphics.PointF
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.bottomsheets.listeners.VEIListenerBottomSheetFill
import good.damn.editor.vector.extensions.views.bounds
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.core.shapes.fill.VEMFillGradientLinear

class VEBottomSheetMakeFill(
    private val toView: ViewGroup,
    private val onConfirmFill: VEIListenerBottomSheetFill<VEIFill>
): VEBottomSheet(
    toView
) {

    override fun onCreateView(
        context: Context
    ) = LinearLayout(
        context
    ).apply {
        orientation = LinearLayout.HORIZONTAL

        val btnWidth = VEApp.width / 2

        Button(
            context
        ).apply {

            text = "Gradient"

            setOnClickListener {
                VEBottomSheetMakeGradient(
                    toView
                ) {
                    onConfirmFill.onConfirmFill(it)
                }.show()
            }

            addView(
                this,
                btnWidth,
                -1
            )
        }

        Button(
            context
        ).apply {
            text = "Color"

            setOnClickListener {
                VEBottomSheetSelectColor(
                    toView
                ) {
                    onConfirmFill.onConfirmFill(it)
                }.show()
            }

            addView(
                this,
                btnWidth,
                -1
            )
        }

        boundsFrame(
            width = VEApp.width.toFloat(),
            height = VEApp.height * 0.2f,
            gravity = Gravity.BOTTOM
        )
    }

}