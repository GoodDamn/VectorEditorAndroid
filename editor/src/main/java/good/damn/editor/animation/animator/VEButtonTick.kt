package good.damn.editor.animation.animator

import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import good.damn.editor.animation.animator.scroller.VEScrollerHorizontal
import good.damn.editor.animation.animator.scroller.vertical.VEScrollerVertical
import good.damn.editor.animation.animator.ticker.VEAnimatorTicker
import good.damn.sav.misc.extensions.primitives.isInRange
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VEITouchable
import kotlin.math.abs

class VEButtonTick(
    private val mScrollerVertical: VEScrollerVertical,
    private val mTicker: VEAnimatorTicker,
    private val mScrollerHorizontal: VEScrollerHorizontal
): VEITouchable {

    companion object {
        private const val COLOR_DEFAULT = 0xffff0000.toInt()
        private const val COLOR_SELECT = 0xff00ff00.toInt()
    }

    var durationPx = 0
    var duration = 0

    override fun onTouchEvent(
        event: MotionEvent
    ) = when (event.action) {
        MotionEvent.ACTION_UP,
        MotionEvent.ACTION_CANCEL -> {
            /*options?.forEach {
                val y = it.y + mScrollerVertical.scrollValue
                if (y > 0
                    && event.x < it.width
                    && event.y.isInRange(
                        y,
                        y+it.height
                    )) {
                    val fa = (abs(mScrollerHorizontal
                        .scrollValue
                    ) + mTicker.tickPosition) / durationPx

                    it.tickTimer.tick(
                        duration,
                        fa
                    )
                }
            }*/
            false
        }
        else -> true
    }

}