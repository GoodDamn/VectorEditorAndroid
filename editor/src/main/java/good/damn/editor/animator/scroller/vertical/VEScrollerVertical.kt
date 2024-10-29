package good.damn.editor.animator.scroller.vertical

import android.view.MotionEvent
import good.damn.editor.animator.scroller.VEIScroller
import good.damn.sav.misc.interfaces.VEITouchable

class VEScrollerVertical
: VEIScroller(),
VEITouchable {

    var triggerEndX: Float = 0f

    var onScroll: VEIListenerOnScrollVertical? = null

    override var scrollValue = 0f
    override var mAnchorValue = 0f
    override var mScrollValue = 0f

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        if (event.x > triggerEndX) {
            return false
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mAnchorValue = event.y
            }

            MotionEvent.ACTION_MOVE -> {
                scrollValue = mScrollValue + (event.y - mAnchorValue)
                if (scrollValue > 0.0f) {
                    scrollValue = 0.0f
                }

                onScroll?.onScrollVertical(
                    scrollValue
                )
            }

            MotionEvent.ACTION_UP -> {
                mScrollValue = scrollValue
            }
        }

        return true
    }

}