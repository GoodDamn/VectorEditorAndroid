package good.damn.sav.core.animation.animators

import android.animation.ValueAnimator
import android.util.Log
import good.damn.sav.misc.scopes.TimeScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class VEAnimatorGlobal {

    companion object {
        private val TAG = VEAnimatorGlobal::class.simpleName
    }

    var isPlaying: Boolean = false
        private set

    var onUpdateFrameAnimation: VEIListenerAnimationUpdateFrame? = null

    private val mScope = TimeScope(
        Dispatchers.IO
    )

    private var mJobPlay: Job? = null

    fun stop() {
        isPlaying = false
        mJobPlay?.cancel()
    }

    fun play(
        atTimeMs: Long,
        animations: List<VEIListenerAnimation>
    ) {
        if (isPlaying) {
            stop()
            return
        }

        isPlaying = true

        mJobPlay = mScope.launch {
            playJob(
                atTimeMs,
                animations
            )
        }
    }

    private suspend inline fun playJob(
        atTimeMs: Long,
        animations: List<VEIListenerAnimation>
    ) {
        var currentMs = atTimeMs
        var dt: Long
        val animsCount = animations.size
        var animsCompleted = 0
        var state: VEEnumAnimationState

        animations.forEach {
            it.animationStart()
        }

        mScope.remember()

        while (isPlaying) {
            dt = mScope.deltaTime
            if (dt == 0L) {
                continue
            }

            animations.forEach {
                state = it.animationTickContinuation(dt)
                if (state == VEEnumAnimationState.HAS_END) {
                    animsCompleted++
                }
            }

            onUpdateFrameAnimation?.onUpdateFrameAnimation()

            if (animsCount == animsCompleted) {
                return
            }

            currentMs += dt
            mScope.remember()
        }
    }

}