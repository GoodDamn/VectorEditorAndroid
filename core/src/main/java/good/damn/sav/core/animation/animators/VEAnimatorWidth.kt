package good.damn.sav.core.animation.animators

import android.view.animation.DecelerateInterpolator
import good.damn.sav.core.animation.keyframe.VEMKeyframePosition
import good.damn.sav.core.animation.keyframe.VEMKeyframeWidth
import good.damn.sav.core.shapes.VEShapeBase

class VEAnimatorWidth(
    private val shape: VEShapeBase,
    iterator: Iterator<VEMKeyframeWidth>,
    duration: Int
): VEAnimatorBase<VEMKeyframeWidth>(
    DecelerateInterpolator(),
    iterator,
    duration
) {
    override fun onNextFrame(
        start: VEMKeyframeWidth,
        end: VEMKeyframeWidth,
        factor: Float
    ) {
        shape.strokeWidth = interpolateValue(
            start.strokeWidth,
            end.strokeWidth,
            factor
        )
    }
}