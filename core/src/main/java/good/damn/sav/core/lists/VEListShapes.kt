package good.damn.sav.core.lists

import good.damn.sav.core.listeners.VEICallbackOnAddShape
import good.damn.sav.core.shapes.VEShapeBase
import java.util.LinkedList

class VEListShapes
: LinkedList<VEShapeBase>() {

    var onAddShape: VEICallbackOnAddShape? = null

    fun find(
        touchX: Float,
        touchY: Float
    ): VEShapeBase? {
        forEach {
            if (it.checkHit(
                    touchX,
                    touchY
                )) {
                return it
            }
        }

        return null
    }

    override fun add(
        element: VEShapeBase
    ): Boolean {
        element.index = (size shl 16) or 0x0000ffff
        onAddShape?.onAddShape(
            element
        )
        return super.add(
            element
        )
    }
}