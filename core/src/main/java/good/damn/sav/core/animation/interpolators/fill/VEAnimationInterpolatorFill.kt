package good.damn.sav.core.animation.interpolators.fill

import good.damn.sav.core.animation.interpolators.VEIAnimationInterpolator
import good.damn.sav.core.animation.keyframe.fill.VEMKeyframeFill
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.fill.VEIFill

class VEAnimationInterpolatorFill(
    override val start: VEMKeyframeFill,
    override val end: VEMKeyframeFill,
    private val observer: VEAnimationObserverFill
): VEIAnimationInterpolator {

    private val mPriorityFill = if (
        start.fillPriority.priority > end.fillPriority.priority
    ) start.fillPriority else end.fillPriority

    override fun interpolate(
        factor: Float
    ) {
        observer.value = mPriorityFill.priorityInterpolate(
            factor,
            start.fillPriority,
            end.fillPriority
        )
    }
}