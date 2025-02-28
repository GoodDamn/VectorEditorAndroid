package good.damn.sav.core.animation.interpolators.fill

import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.fill.VEIFill
import java.util.LinkedList

class VEAnimationObserverFill(
    value: VEIFill
) {

    private val mList = LinkedList<VEShapeBase>()

    var value = value
        set(v) {
            field = v
            mList.forEach {
                it.fill = v
            }
        }

    fun observe(
        shape: VEShapeBase
    ) {
        mList.add(
            shape
        )
    }

    fun removeObserver(
        shape: VEShapeBase
    ) {
        mList.remove(
            shape
        )
    }

}