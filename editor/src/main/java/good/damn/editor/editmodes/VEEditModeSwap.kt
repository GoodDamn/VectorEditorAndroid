package good.damn.editor.editmodes

import android.graphics.Matrix
import android.view.MotionEvent
import good.damn.sav.misc.interfaces.VEITouchable

open class VEEditModeSwap<T: VEEditMode>(
    val editModes: Array<T>
): VEEditModeTransformed() {

    protected var mCurrentEditMode: T? = null

    override fun onTouchEvent(
        event: MotionEvent,
        invertedMatrix: Matrix
    ): Boolean {
        super.onTouchEvent(
            event,
            invertedMatrix
        )

        mCurrentEditMode?.apply {
            if (!onTouchEvent(event, invertedMatrix)) {
                mCurrentEditMode = null
            }
            return true
        }

        editModes.forEach {
            if (it.onTouchEvent(event, invertedMatrix)) {
                mCurrentEditMode = it
                return true
            }
        }

        return false
    }

}