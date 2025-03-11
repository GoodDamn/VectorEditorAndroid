package good.damn.editor.editmodes

import android.graphics.Matrix
import android.view.MotionEvent

open class VEEditModeTransformed
: VEEditMode {

    override fun onTouchEvent(
        event: MotionEvent,
        invertedMatrix: Matrix
    ): Boolean {
        event.transform(invertedMatrix)
        return true
    }

}