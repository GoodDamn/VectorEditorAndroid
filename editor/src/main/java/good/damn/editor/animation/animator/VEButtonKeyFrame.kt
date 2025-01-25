package good.damn.editor.animation.animator

import android.view.MotionEvent
import android.view.View
import good.damn.sav.misc.interfaces.VEILayoutable
import good.damn.sav.misc.interfaces.VEITouchable

class VEButtonKeyFrame
: VEITouchable,
VEILayoutable {

    companion object {
        private const val COLOR_DEFAULT = 0xffff0000.toInt()
        private const val COLOR_SELECT = 0xff00ff00.toInt()
    }

    var x = 0f
        private set

    var y = 0f
        private set

    var width = 0f
        private set

    var height = 0f
        private set

    var onClickListener: View.OnClickListener? = null

    override fun layout(
        x: Float,
        y: Float,
        width: Float,
        height: Float
    ) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
    }

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (x > event.x || x + width < event.x ||
                    y > event.y || y + height < event.y
                ) {
                    return false
                }
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                if (x > event.x || x + width < event.x ||
                    y > event.y || y + height < event.y
                ) {
                    return false
                }

                onClickListener?.onClick(null)
                return false
            }
        }

        return true
    }

}