package good.damn.editor.animation.animators

import android.os.Handler
import android.os.Looper
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import good.damn.sav.misc.structures.tree.toList
import java.util.LinkedList
import kotlin.time.Duration

class VEAnimatorTick: VEIListenerAnimation {

    private val mHandler = Handler(
        Looper.getMainLooper()
    )


    override fun onAnimatorStart() {

    }

    override fun onAnimatorTick(
        currentTimeMs: Long,
        dt: Long,
        duration: Int
    ) {
        val d = dt.toFloat() / duration

        /*mScrollerHorizontal.scrollValue =
            currentTimeMs / metadata.duration * -metadata.durationPx

        options?.forEach {
            tickOption(
                it, d
            )
        }*/
    }

    override fun onAnimatorEnd() {

    }

    /*private inline fun tickOption(
        it: VEModelOption,
        dt: Float
    ) = it.run {
        currentPathTime += dt
        if (currentPathTime > to.tickFactor) {
            from = to
            if (!ticks.hasNext()) {
                return@run
            }
            to = ticks.next()
            dtPath = to.tickFactor - from.tickFactor
            return@run
        }

        val i = (currentPathTime - from.tickFactor) / dtPath

        interpolation.interpolate(
            from,
            to,
            mInterpolator.getInterpolation(i)
        )
    }*/
}