package good.damn.sav.core.animation.keyframe

import good.damn.sav.core.shapes.fill.VEIFill

interface VEIInterpolatablePriority {
    val priority: Int

    fun priorityInterpolate(
        factor: Float,
        start: VEIInterpolatablePriority,
        end: VEIInterpolatablePriority
    ): VEIFill

    fun requestValue(
        index: Int
    ): ByteArray
}