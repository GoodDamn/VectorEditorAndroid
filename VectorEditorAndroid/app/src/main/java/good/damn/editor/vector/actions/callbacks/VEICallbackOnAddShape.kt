package good.damn.editor.vector.actions.callbacks

import good.damn.editor.vector.shapes.VEShapeBase

interface VEICallbackOnAddShape {
    fun onAddShape(
        shape: VEShapeBase
    )
}