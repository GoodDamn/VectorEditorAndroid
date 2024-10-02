package good.damn.editor.vector.actions.callbacks

import good.damn.editor.vector.paints.VEPaintBase

interface VEICallbackOnAddShape {
    fun onAddShape(
        shape: VEPaintBase
    )
}