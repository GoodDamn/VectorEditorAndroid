package good.damn.sav.core.animation.animators

import android.view.animation.DecelerateInterpolator
import good.damn.sav.core.animation.keyframe.VEMKeyframePosition
import good.damn.sav.core.animation.keyframe.VEMKeyframeWidth
import good.damn.sav.core.shapes.VEShapeBase

class VEAnimatorWidth(
    private val shape: VEShapeBase,
    list: List<VEMKeyframeWidth>
): VEAnimatorBase<VEMKeyframeWidth>(
    DecelerateInterpolator(),
    list
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