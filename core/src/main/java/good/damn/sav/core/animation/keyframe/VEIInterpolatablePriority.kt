package good.damn.sav.core.animation.keyframe

import good.damn.sav.core.shapes.fill.VEIFill

interface VEIInterpolatablePriority {
    val priority: Int

    fun startInterpolate()

    fun priorityInterpolate(
        factor: Float,
        start: VEIInterpolatablePriority,
        end: VEIInterpolatablePriority
    ): VEIFill

    fun nextInterpolateValue(
        index: Int
    ): ByteArray
}