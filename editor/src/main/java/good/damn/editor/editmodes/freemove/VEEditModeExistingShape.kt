package good.damn.editor.editmodes.freemove

import android.graphics.Matrix
import android.view.MotionEvent
import good.damn.editor.editmodes.VEEditModeTransformed
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectShape
import good.damn.sav.core.lists.VEListShapes

class VEEditModeExistingShape(
    private val mShapes: VEListShapes
): VEEditModeTransformed() {

    var onSelectShape: VEIListenerOnSelectShape? = null

    override fun onTouchEvent(
        event: MotionEvent,
        invertedMatrix: Matrix
    ): Boolean {
        super.onTouchEvent(
            event,
            invertedMatrix
        )

        if (event.action == MotionEvent.ACTION_DOWN) {
            mShapes.find(
                event.x,
                event.y
            )?.apply {
                onSelectShape?.onSelectShape(
                    this
                )
            }
        }

        return false
    }
}