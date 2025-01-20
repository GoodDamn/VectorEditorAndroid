package good.damn.editor.animation.animators

import good.damn.sav.misc.scopes.TimeScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class VEAnimator {

    companion object {
        private val TAG = VEAnimator::class.simpleName
    }

    var isPlaying: Boolean = false
        private set

    var duration: Int = 1000

    var listener: VEIListenerAnimation? = null

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

        listener?.onAnimatorStart()

        while (currentMs < duration && isPlaying) {
            dt = mScope.deltaTime
            if (dt == 0L) {
                continue
            }
            listener?.onAnimatorTick(
                currentMs,
                dt,
                duration
            )

            currentMs += dt
            mScope.remember()
        }

        listener?.onAnimatorEnd()
    }

}