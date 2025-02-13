package good.damn.sav.core.animation.interpolators.fill

import good.damn.sav.core.animation.keyframe.VEIInterpolatablePriority
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.core.shapes.fill.VEMFillColor
import good.damn.sav.misc.extensions.interpolate

class VEMFillColorPriority(
    private val fillColor: VEMFillColor
): VEIInterpolatablePriority {

    override val priority = 0

    private val mInterpolatedColor = VEMFillColor(
        ByteArray(4)
    )

    override fun priorityInterpolate(
        factor: Float,
        start: VEIInterpolatablePriority,
        end: VEIInterpolatablePriority
    ): VEIFill {
        mInterpolatedColor.color.interpolate(
            start.requestValue(0),
            end.requestValue(0),
            factor
        )
        return mInterpolatedColor
    }

    override fun requestValue(
        index: Int
    ) = fillColor.color
}