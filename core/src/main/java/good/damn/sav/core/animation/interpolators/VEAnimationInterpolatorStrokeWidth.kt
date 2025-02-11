package good.damn.sav.core.animation.interpolators

import good.damn.sav.core.animation.keyframe.VEIKeyframe
import good.damn.sav.core.animation.keyframe.VEMKeyframeWidth
import good.damn.sav.core.animation.keyframe.fill.VEMKeyframeFill
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.misc.extensions.primitives.interpolate

class VEAnimationInterpolatorStrokeWidth(
    override val start: VEMKeyframeWidth,
    override val end: VEMKeyframeWidth,
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