package good.damn.sav.core.lists

import good.damn.sav.core.listeners.VEICallbackOnAddShape
import good.damn.sav.core.shapes.VEShapeBase
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