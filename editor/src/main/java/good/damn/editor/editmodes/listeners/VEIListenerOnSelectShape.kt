package good.damn.editor.editmodes.listeners

import good.damn.sav.core.shapes.VEShapeBase

interface VEIListenerOnSelectShape {
    fun onSelectShape(
        shape: VEShapeBase
    )
}