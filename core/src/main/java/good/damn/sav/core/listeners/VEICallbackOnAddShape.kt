package good.damn.sav.core.listeners

import good.damn.sav.core.shapes.VEShapeBase

interface VEICallbackOnAddShape {
    fun onAddShape(
        shape: VEShapeBase
    )
}