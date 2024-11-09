package good.damn.sav.core.lists

import good.damn.sav.core.listeners.VEICallbackOnAddShape
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.misc.structures.tree.LinkedList2
import java.util.LinkedList

class VEListShapes: LinkedList2<VEShapeBase>() {

    var onAddShape: VEICallbackOnAddShape? = null
    
    override fun add(
        e: VEShapeBase
    ) {
        onAddShape?.onAddShape(e)
        super.add(e)
    }
}