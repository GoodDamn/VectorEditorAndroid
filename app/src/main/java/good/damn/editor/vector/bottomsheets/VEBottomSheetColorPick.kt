package good.damn.editor.vector.bottomsheets

import android.content.Context
import android.view.View
import android.view.ViewGroup
import good.damn.gradient_color_picker.GradientColorPicker
import good.damn.gradient_color_picker.OnPickColorListener

class VEBottomSheetColorPick(
    group: ViewGroup
): VEBottomSheet(
    group
) {

    var onPickColor: OnPickColorListener? = null

    override fun onCreateView(
        context: Context
    ) = GradientColorPicker(
        context
    ).apply {
        setOnPickColorListener(
            onPickColor
        )
    }
}