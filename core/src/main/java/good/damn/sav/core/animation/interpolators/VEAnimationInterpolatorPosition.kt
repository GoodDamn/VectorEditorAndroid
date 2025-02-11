package good.damn.sav.core.animation.interpolators

import android.graphics.PointF
import android.view.animation.OvershootInterpolator
import good.damn.sav.core.animation.keyframe.VEIKeyframe
import good.damn.sav.core.animation.keyframe.VEMKeyframePosition
import good.damn.sav.misc.extensions.primitives.interpolate

class VEAnimationInterpolatorPosition(
    override val start: VEMKeyframePosition,
    override val end: VEMKeyframePosition,
    private val point: PointF
): VEIAnimationInterpolator {

    private val interpolator = OvershootInterpolator(2.0f)
    private var fint = 0f

    override fun interpolate(
        factor: Float
    ) {
        fint = interpolator.getInterpolation(
            factor
        )

        point.x = start.x.interpolate(
            end.x,
            fint
        )

        point.y = start.y.interpolate(
            end.y,
            fint
        )
    }
}