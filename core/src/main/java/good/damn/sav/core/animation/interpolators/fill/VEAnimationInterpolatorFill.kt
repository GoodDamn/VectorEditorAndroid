package good.damn.sav.core.animation.interpolators.fill

import android.util.Log
import good.damn.sav.core.animation.interpolators.VEIAnimationInterpolator
import good.damn.sav.core.animation.keyframe.fill.VEMKeyframeFill
import good.damn.sav.core.shapes.VEShapeBase

class VEAnimationInterpolatorFill(
    override val start: VEMKeyframeFill,
    override val end: VEMKeyframeFill,
    private val shape: VEShapeBase
): VEIAnimationInterpolator {

    private val mPriorityKeyframe = if (
        start.fill.priority > end.fill.priority
    )start else end

    init {
        start.fill.startInterpolate()
        end.fill.startInterpolate()
    }

    override fun interpolate(
        factor: Float
    ) {
        shape.fill = mPriorityKeyframe.fill.priorityInterpolate(
            factor,
            start.fill,
            end.fill
        )
    }
}