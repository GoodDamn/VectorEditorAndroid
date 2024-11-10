package good.damn.editor.editmodes

import android.view.MotionEvent
import good.damn.sav.misc.interfaces.VEITouchable

open class VEEditModeSwap(
    val editModes: Array<VEITouchable>
): VEITouchable {

    private var mCurrentEditMode: VEITouchable? = null

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        mCurrentEditMode?.apply {
            if (!onTouchEvent(event)) {
                mCurrentEditMode = null
            }
            return true
        }

        editModes.forEach {
            if (it.onTouchEvent(event)) {
                mCurrentEditMode = it
                return true
            }
        }

        return false
    }

}