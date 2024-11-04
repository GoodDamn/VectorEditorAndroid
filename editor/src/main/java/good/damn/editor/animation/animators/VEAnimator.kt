package good.damn.editor.animation.animators

import android.util.Log
import good.damn.sav.misc.scopes.TimeScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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

    fun pause() {
        isPlaying = false
        mJobPlay?.cancel()
    }

    fun play(
        atTimeMs: Long
    ) {
        if (isPlaying) {
            pause()
            return
        }

        isPlaying = true

        mJobPlay = mScope.launch {
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
                    dt
                )

                currentMs += dt
                mScope.remember()
            }

            listener?.onAnimatorEnd()
        }
    }

}