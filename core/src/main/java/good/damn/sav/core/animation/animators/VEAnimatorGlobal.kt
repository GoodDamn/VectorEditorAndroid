package good.damn.sav.core.animation.animators

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

    private var mAnimations: Array<VEIListenerTick>? = null

    private val mScope = TimeScope(
        Dispatchers.IO
    )

    private var mJobPlay: Job? = null

    fun stop() {
        isPlaying = false
        mJobPlay?.cancel()
    }

    fun play(
        atTimeMs: Long
    ) {
        if (isPlaying) {
            stop()
            return
        }

        isPlaying = true

        mJobPlay = mScope.launch {
            playJob(atTimeMs)
        }
    }

    private inline fun playJob(
        atTimeMs: Long
    ) {
        var currentMs = atTimeMs
        var dt: Long
        mScope.remember()

        val animsCount = mAnimations?.size ?: 0
        var animsCompleted = 0
        var state = VEEnumAnimationState.RUNNING

        while (isPlaying) {
            dt = mScope.deltaTime
            if (dt == 0L) {
                continue
            }

            mAnimations?.forEach {
                state = it.animationTickContinuation(dt)
                if (state == VEEnumAnimationState.HAS_END) {
                    animsCompleted++
                }
            }

            if (animsCount == animsCompleted) {
                return
            }

            currentMs += dt
            mScope.remember()
        }
    }

}