package good.damn.sav.core.animation.animators

import android.animation.TimeInterpolator
import android.graphics.PointF
import good.damn.sav.core.animation.keyframe.VEIKeyframe
import good.damn.sav.core.animation.keyframe.VEMKeyframePosition

abstract class VEAnimatorBase<
T: VEIKeyframe
>(
    private val interpolator: TimeInterpolator,
    private val keyframes: List<T>
): VEIListenerAnimation {

    companion object {
        private val TAG = VEAnimatorBase::class.simpleName
    }

    override var duration = 1000

    private var mCurrentFactor = 0f
    private var mCurrentSubFactor = 0f

    private var mCurrentDt = 0f

    private lateinit var mStartKeyframe: T
    private lateinit var mEndKeyframe: T
    private lateinit var mKeyframeIterator: Iterator<T>

    private var mSubDurationFactor = 0f

    private var mHasEnded = false

    final override fun animationStart() {
        mCurrentFactor = 0f
        mCurrentSubFactor = 0f
        mHasEnded = false

        mKeyframeIterator = keyframes.iterator()

        mStartKeyframe = mKeyframeIterator.next()
        mEndKeyframe = mKeyframeIterator.next()

        mSubDurationFactor = mEndKeyframe.factor - mStartKeyframe.factor

        onNextFrame(
            mStartKeyframe,
            mEndKeyframe,
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
        
        if (mCurrentFactor < mStartKeyframe.factor) {
            return VEEnumAnimationState.RUNNING
        }

        mCurrentSubFactor = (
            mCurrentFactor - mStartKeyframe.factor
        ) / mSubDurationFactor

        if (mCurrentFactor > mEndKeyframe.factor) {
            mStartKeyframe = mEndKeyframe

            if (!mKeyframeIterator.hasNext()) {
                mHasEnded = true
                return VEEnumAnimationState.HAS_END
            }

            mEndKeyframe = mKeyframeIterator.next()

            mSubDurationFactor = mEndKeyframe.factor - mStartKeyframe.factor
        }

        onNextFrame(
            mStartKeyframe,
            mEndKeyframe,
            interpolator.getInterpolation(
                mCurrentSubFactor
            )
        )

        return VEEnumAnimationState.RUNNING
    }


    protected abstract fun onNextFrame(
        start: T,
        end: T,
        factor: Float
    )

    protected inline fun interpolateValue(
        start: Float,
        end: Float,
        factor: Float
    ) = start + (end - start) * factor


}