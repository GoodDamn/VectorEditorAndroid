package good.damn.editor.editmodes

import android.graphics.Matrix
import android.view.MotionEvent

interface VEEditMode {
    fun onTouchEvent(
        event: MotionEvent,
        invertedMatrix: Matrix
    ): Boolean
}