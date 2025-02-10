package good.damn.editor.vector.bottomsheets

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.bottomsheets.listeners.VEIListenerBottomSheetFill
import good.damn.editor.vector.extensions.views.bounds
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.editor.vector.extensions.views.boundsLinear
import good.damn.gradient_color_picker.GradientColorPicker
import good.damn.gradient_color_picker.OnPickColorListener
import good.damn.lib.verticalseekbar.VSViewSeekBarV
import good.damn.lib.verticalseekbar.interfaces.VSIListenerSeekBarProgress
import good.damn.sav.core.shapes.fill.VEMFillColor
import good.damn.sav.core.shapes.fill.VEMFillGradientLinear

class VEBottomSheetSelectColor(
    toView: ViewGroup,
    private val onConfirmFill: VEIListenerBottomSheetFill
): VEBottomSheet(
    toView
), OnPickColorListener {

    private val mFillColor = VEMFillColor(0)

    override fun onCreateView(
        context: Context
    ) = GradientColorPicker(
        context
    ).apply {

        setOnPickColorListener(
            this@VEBottomSheetSelectColor
        )

        boundsFrame(
            gravity = Gravity.BOTTOM,
            bottom = VEApp.height * 0.1f,
            width = VEApp.width.toFloat(),
            height = VEApp.height * 0.3f
        )
    }

    override fun onPickColor(
        color: Int
    ) {
        mFillColor.color = color
        onConfirmFill.onConfirmFill(
            mFillColor
        )
    }

}