package good.damn.sav.core.animation.animators

class VEAnimatorPosition
: VEIListenerTick {
    var duration: Int = 0

    private var mCurrent = 0

    override fun animationTickContinuation(
        dt: Long
    ): VEEnumAnimationState {
        if (mCurrent > duration) {
            return VEEnumAnimationState.END
        }



        mCurrent += dt.toInt()
        if (mCurrent > duration) {
            return VEEnumAnimationState.HAS_END
        }

        return VEEnumAnimationState.RUNNING
    }
}