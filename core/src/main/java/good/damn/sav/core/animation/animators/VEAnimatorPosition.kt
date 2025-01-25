package good.damn.sav.core.animation.animators

import android.graphics.PointF
import android.view.animation.AccelerateDecelerateInterpolator
import good.damn.sav.core.animation.keyframe.VEMKeyframePosition
import kotlin.time.Duration

class VEAnimatorPosition(
    private val point: PointF,
    iterator: Iterator<VEMKeyframePosition>,
    duration: Int
): VEAnimatorBase<VEMKeyframePosition>(
    AccelerateDecelerateInterpolator(),
    iterator,
    duration
) {
    override fun onNextFrame(
        start: VEMKeyframePosition,
        end: VEMKeyframePosition,
        factor: Float
    ) {
        point.x = interpolateValue(
            start.x,
            end.x,
            factor
        )

        point.y = interpolateValue(
            start.y,
            end.y,
            factor
        )
    }

}