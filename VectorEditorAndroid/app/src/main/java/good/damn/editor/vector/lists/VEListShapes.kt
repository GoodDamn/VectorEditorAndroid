package good.damn.editor.vector.lists

import good.damn.editor.vector.actions.callbacks.VEICallbackOnAddShape
import good.damn.editor.vector.paints.VEPaintBase
import java.util.LinkedList

class VEListShapes: LinkedList<VEPaintBase>() {
    var onAddShape: VEICallbackOnAddShape? = null

    override fun add(
        element: VEPaintBase
    ): Boolean {
        onAddShape?.onAddShape(
            element
        )
        return super.add(element)
    }
}