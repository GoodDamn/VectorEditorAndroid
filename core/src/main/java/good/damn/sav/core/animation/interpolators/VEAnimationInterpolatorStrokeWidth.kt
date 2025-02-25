package good.damn.sav.core.animation.interpolators

import good.damn.sav.core.animation.keyframe.VEMKeyframeStrokeWidth
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.misc.extensions.primitives.interpolate

class VEAnimationInterpolatorStrokeWidth(
    override val start: VEMKeyframeStrokeWidth,
    override val end: VEMKeyframeStrokeWidth,
    private val shape: VEShapeBase
): VEIAnimationInterpolator {

    override fun interpolate(
        factor: Float
    ) {
        shape.strokeWidth = start.strokeWidth.interpolate(
            end.strokeWidth,
            factor
        )
    }
}