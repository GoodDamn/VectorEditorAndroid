package good.damn.sav.core.animation.animators

interface VEIListenerTick {
    fun animationTickContinuation(
        dt: Long
    ): VEEnumAnimationState
}