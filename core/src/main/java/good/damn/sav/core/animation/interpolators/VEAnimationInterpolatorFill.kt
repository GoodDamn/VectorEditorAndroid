package good.damn.sav.core.animation.interpolators

import good.damn.sav.core.animation.keyframe.VEIKeyframe
import good.damn.sav.core.animation.keyframe.fill.VEMKeyframeFill
import good.damn.sav.core.shapes.VEShapeBase

class VEAnimationInterpolatorFill(
    override val start: VEMKeyframeFill,
    override val end: VEMKeyframeFill,
    private val shape: VEShapeBase
): VEIAnimationInterpolator {

    override fun interpolate(
        factor: Float
    ) {

    }
}