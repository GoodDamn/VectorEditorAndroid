package good.damn.editor.editmodes.listeners

import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.fill.VEIFill

interface VEIListenerOnSelectFill {
    fun onSelectFill(
        fill: VEIFill
    )
}