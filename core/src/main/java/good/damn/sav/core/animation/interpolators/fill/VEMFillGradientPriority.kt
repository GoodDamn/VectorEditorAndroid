package good.damn.sav.core.animation.interpolators.fill

import good.damn.sav.core.animation.keyframe.VEIInterpolatablePriority
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.core.shapes.fill.VEMFillColor
import good.damn.sav.core.shapes.fill.VEMFillGradientLinear
import good.damn.sav.misc.extensions.interpolate
import good.damn.sav.misc.extensions.primitives.toByteArray
import good.damn.sav.misc.extensions.toInt32

class VEMFillGradientPriority(
    private val fillGradient: VEMFillGradientLinear
): VEIInterpolatablePriority {

    override val priority = 1

    private val mInterpolatedColor = ByteArray(4)

    private val mColorBytes = fillGradient.colors.map {
        it.toByteArray()
    }

    private val mInterpolatedColors = IntArray(
        mColorBytes.size
    )

    override fun priorityInterpolate(
        factor: Float,
        start: VEIInterpolatablePriority,
        end: VEIInterpolatablePriority
    ): VEIFill {
        for (i in mColorBytes.indices) {
            mInterpolatedColor.interpolate(
                start.requestValue(i),
                end.requestValue(i),
                factor
            )
            mInterpolatedColors[i] = mInterpolatedColor.toInt32()
        }

        return VEMFillGradientLinear(
            fillGradient.p0x,
            fillGradient.p0y,
            fillGradient.p1x,
            fillGradient.p1y,
            mInterpolatedColors,
            fillGradient.positions
        )
    }

    override fun requestValue(
        index: Int
    ) = mColorBytes[index]

}