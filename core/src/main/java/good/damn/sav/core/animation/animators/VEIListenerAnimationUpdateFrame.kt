package good.damn.sav.core.animation.animators

fun interface VEIListenerAnimationUpdateFrame {
    suspend fun onUpdateFrameAnimation()
}