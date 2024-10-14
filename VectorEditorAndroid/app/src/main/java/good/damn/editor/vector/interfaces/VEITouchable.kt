package good.damn.editor.vector.interfaces

import android.view.MotionEvent

interface VEITouchable {
    fun onTouchEvent(
        event: MotionEvent
    ): Boolean
}