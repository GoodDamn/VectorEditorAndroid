package good.damn.sav.misc.interfaces

import android.view.MotionEvent

interface VEITouchable {
    fun onTouchEvent(
        event: MotionEvent
    ): Boolean
}