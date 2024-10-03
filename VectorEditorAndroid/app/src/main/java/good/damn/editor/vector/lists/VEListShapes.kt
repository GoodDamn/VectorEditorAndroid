package good.damn.editor.vector.lists

import good.damn.editor.vector.actions.callbacks.VEICallbackOnAddShape
import good.damn.editor.vector.shapes.VEShapeBase
import java.util.LinkedList

class VEListShapes: LinkedList<VEShapeBase>() {
    var onAddShape: VEICallbackOnAddShape? = null

    fun resetList(
        list: MutableList<VEShapeBase>
    ) {
        clear()
        addAll(
            list
        )
    }

    override fun add(
        element: VEShapeBase
    ): Boolean {
        onAddShape?.onAddShape(
            element
        )

        return super.add(
            element
        )
    }
}