package good.damn.sav.core.lists

import good.damn.sav.core.listeners.VEICallbackOnAddShape
import good.damn.sav.core.shapes.VEShapeBase
import java.util.LinkedList

class VEListShapes: LinkedList<VEShapeBase>() {

    var onAddShape: VEICallbackOnAddShape? = null
    
    override fun add(
        e: VEShapeBase
    ): Boolean {
        onAddShape?.onAddShape(e)
        return super.add(e)
    }
}