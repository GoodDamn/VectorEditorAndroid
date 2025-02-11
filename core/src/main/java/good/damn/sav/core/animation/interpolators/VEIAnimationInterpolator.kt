package good.damn.sav.core.animation.interpolators

import good.damn.sav.core.animation.keyframe.VEIKeyframe

interface VEIAnimationInterpolator {

    val start: VEIKeyframe
    val end: VEIKeyframe

    val factorGlobalDuration: Float
        get() = end.factor - start.factor

    fun interpolate(
        factor: Float
    )
}