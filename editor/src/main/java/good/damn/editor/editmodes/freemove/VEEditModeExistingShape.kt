package good.damn.editor.editmodes.freemove

import android.view.MotionEvent
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectShape
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.misc.interfaces.VEITouchable

class VEEditModeExistingShape(
    private val mShapes: VEListShapes
): VEITouchable {

    var onSelectShape: VEIListenerOnSelectShape? = null

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
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