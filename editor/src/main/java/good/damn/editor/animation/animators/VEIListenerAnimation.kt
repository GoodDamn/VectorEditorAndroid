package good.damn.editor.animation.animators

interface VEIListenerAnimation {
    fun onAnimatorStart()

    fun onAnimatorTick(
        currentTimeMs: Long,
        dt: Long,
        duration: Int
    )

    fun onAnimatorEnd()
}