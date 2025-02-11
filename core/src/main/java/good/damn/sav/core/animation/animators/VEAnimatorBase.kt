package good.damn.sav.core.animation.animators

import android.util.Log
import good.damn.sav.core.animation.interpolators.VEIAnimationInterpolator

class VEAnimatorBase(
    private val interpolators: List<VEIAnimationInterpolator>
): VEIListenerAnimation {

    companion object {
        private val TAG = VEAnimatorBase::class.simpleName
    }

    override var duration = 1000

    private var mCurrentFactor = 0f
    private var mCurrentSubFactor = 0f

    private var mCurrentDt = 0f

    private var mSubDurationFactor = 0f

    private lateinit var mCurrentInterpolator: VEIAnimationInterpolator
    private lateinit var mIteratorInterpolator: Iterator<VEIAnimationInterpolator>

    private var mHasEnded = false

    final override fun animationStart() {
        mCurrentFactor = 0f
        mCurrentSubFactor = 0f
        mHasEnded = false

        mIteratorInterpolator = interpolators.iterator()
        mCurrentInterpolator = mIteratorInterpolator.next()

        mSubDurationFactor = mCurrentInterpolator.factorGlobalDuration
        mCurrentInterpolator.interpolate(
            0.0f
        )
    }

    final override fun animationTickContinuation(
        dt: Long
    ): VEEnumAnimationState {
        if (mCurrentFactor >= 1.0f || mHasEnded) {
            return VEEnumAnimationState.END
        }
        mCurrentDt = dt.toFloat() / duration
        mCurrentFactor += mCurrentDt

        if (mCurrentFactor >= 1.0f) {
            return VEEnumAnimationState.HAS_END
        }
        
        if (mCurrentFactor < mCurrentInterpolator.start.factor) {
            return VEEnumAnimationState.RUNNING
        }

        mCurrentSubFactor = (
            mCurrentFactor - mCurrentInterpolator.start.factor
        ) / mSubDurationFactor

        if (mCurrentFactor > mCurrentInterpolator.end.factor) {
            if (!mIteratorInterpolator.hasNext()) {
                mHasEnded = true
                return VEEnumAnimationState.HAS_END
            }

            mCurrentInterpolator = mIteratorInterpolator.next()
            mSubDurationFactor = mCurrentInterpolator.factorGlobalDuration
        }

        mCurrentInterpolator.interpolate(
            mCurrentSubFactor
        )

        return VEEnumAnimationState.RUNNING
    }

}