package good.damn.sav.core.animation.keyframe

import android.util.Log
import good.damn.sav.core.animation.interpolators.VEIAnimationInterpolator
import good.damn.sav.misc.structures.tree.BinaryTree
import good.damn.sav.misc.structures.tree.toList
import java.util.LinkedList

class VEKeyframes<T: VEIKeyframe>
: BinaryTree<T>(
    equality = {v, vv -> v.factor == v.factor},
    moreThan = {v, vv -> v.factor > vv.factor}
) {

    inline fun convertToInterpolators(
        addInterpolator: (T, T) -> VEIAnimationInterpolator
    ): List<VEIAnimationInterpolator>? {
        if (size < 2) {
            return null
        }

        val list = LinkedList<VEIAnimationInterpolator>()

        var start: T? = null
        var end: T

        toList().forEach {
            if (start == null) {
                start = it
                return@forEach
            }

            end = it

            list.add(
                addInterpolator(
                    start!!,
                    end
                )
            )

            start = end
        }

        return list
    }

}