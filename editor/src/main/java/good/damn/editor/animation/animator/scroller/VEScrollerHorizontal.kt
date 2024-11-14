package good.damn.editor.animation.animator.scroller

import android.util.Log
import android.view.MotionEvent
import good.damn.sav.misc.interfaces.VEITouchable

class VEScrollerHorizontal
: VEIScroller(), VEITouchable {

    companion object {
        private val TAG = VEScrollerHorizontal::class.simpleName
    }

    var triggerEndX = 0f
    var triggerEndY = 0f

    override var scrollValue = 0f
    override var mAnchorValue = 0f
    override var mScrollValue = 0f

    override fun reset() {
        scrollValue = 0f
        mAnchorValue = 0f
        mScrollValue = 0f
    }

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        if (event.x < triggerEndX || event.y < triggerEndY) {
            return false
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mAnchorValue = event.x
            }

            MotionEvent.ACTION_MOVE -> {
                scrollValue = mScrollValue + (event.x - mAnchorValue)
                if (scrollValue > 0.0f) {
                    scrollValue = 0.0f
                }
            }

            MotionEvent.ACTION_UP -> {
                mScrollValue = scrollValue
            }
        }

        return true
    }

}