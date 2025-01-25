package good.damn.sav.core.animation.animators

interface VEIListenerAnimation {

    fun animationStart()

    fun animationTickContinuation(
        dt: Long
    ): VEEnumAnimationState
}