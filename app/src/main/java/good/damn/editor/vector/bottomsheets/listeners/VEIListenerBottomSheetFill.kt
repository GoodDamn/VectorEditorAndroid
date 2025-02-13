package good.damn.editor.vector.bottomsheets.listeners

import good.damn.sav.core.shapes.fill.VEIFill

fun interface VEIListenerBottomSheetFill<T: VEIFill> {
    fun onConfirmFill(
        fill: T?
    )
}