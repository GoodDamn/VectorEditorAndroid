package good.damn.editor.vector.bottomsheets.listeners

import good.damn.sav.core.shapes.fill.VEIFill

fun interface VEIListenerBottomSheetFill {
    fun onConfirmFill(
        fill: VEIFill?
    )
}