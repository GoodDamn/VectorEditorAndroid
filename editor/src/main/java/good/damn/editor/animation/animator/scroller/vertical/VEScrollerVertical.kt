package good.damn.editor.animation.animator.scroller.vertical

import android.view.MotionEvent
import good.damn.editor.animation.animator.scroller.VEIScroller
import good.damn.sav.misc.interfaces.VEITouchable

class VEScrollerVertical
: VEIScroller(),
VEITouchable {

    var triggerEndX: Float = 0f

    var onScroll: VEIListenerOnScrollVertical? = null

    override var scrollValue = 0f
    override var mAnchorValue = 0f
    override var mScrollValue = 0f

    override fun reset() {
        mAnchorValue = 0f
        mScrollValue = 0f
        scrollValue = 0f
    }

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.x > triggerEndX) {
                    return false
                }

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

            MotionEvent.ACTION_CANCEL,
            MotionEvent.ACTION_UP -> {
                mScrollValue = scrollValue
                return false
            }
        }

        return true
    }

}