package good.damn.editor.vector.bottomsheets

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import good.damn.colorpicker.GradientColorPicker
import good.damn.colorpicker.OnPickColorListener
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.bottomsheets.listeners.VEIListenerBottomSheetFill
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.sav.core.shapes.fill.VEMFillColor
import good.damn.sav.misc.extensions.primitives.toByteArray

class VEBottomSheetSelectColor(
    toView: ViewGroup,
    private val onConfirmFill: VEIListenerBottomSheetFill<VEMFillColor>
): VEBottomSheet(
    toView
), OnPickColorListener {

    private val mFillColor = VEMFillColor(byteArrayOf(0))

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
        mFillColor.color = color.toByteArray()
        onConfirmFill.onConfirmFill(
            mFillColor
        )
    }

}