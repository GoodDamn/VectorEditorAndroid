package good.damn.editor.views.animator.scroller

import android.util.Log
import android.view.MotionEvent
import good.damn.sav.misc.interfaces.VEITouchable

class VEScrollerHorizontal
: VEITouchable {

    companion object {
        private val TAG = VEScrollerHorizontal::class.simpleName
    }

    var scrollValue = 0f
        private set

    var triggerEndY = 0f
    var triggerEndX = 0f

    private var mAnchorX = 0f
    private var mScrollX = 0f

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        if (event.x < triggerEndX || event.y < triggerEndY) {
            return false
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mAnchorX = event.x
            }

            MotionEvent.ACTION_MOVE -> {
                scrollValue = mScrollX + (event.x - mAnchorX)
                if (scrollValue > 0.0f) {
                    scrollValue = 0.0f
                }
            }

            MotionEvent.ACTION_UP -> {
                mScrollX = scrollValue
            }
        }

        return true
    }

}