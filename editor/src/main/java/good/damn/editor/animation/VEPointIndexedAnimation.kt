package good.damn.editor.animation

import good.damn.editor.animation.animator.options.VEOptionAnimatorBase
import good.damn.sav.core.points.VEPointIndexed

data class VEPointIndexedAnimation(
    val point: VEPointIndexed,
    val options: Array<VEOptionAnimatorBase>
) {

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return point.hashCode()
    }
}