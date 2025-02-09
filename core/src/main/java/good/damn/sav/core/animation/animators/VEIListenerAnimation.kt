package good.damn.sav.core.animation.animators

interface VEIListenerAnimation {

    var duration: Int

    fun animationStart()

    fun animationTickContinuation(
        dt: Long
    ): VEEnumAnimationState
}