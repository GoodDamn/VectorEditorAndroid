package good.damn.editor.editmodes

import android.graphics.PointF
import android.view.MotionEvent
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectPoint
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.interfaces.VEITouchable

class VEEditModeAnimation(
    private val skeleton: VESkeleton2D
): VEITouchable {

    var onSelectPoint: VEIListenerOnSelectPoint? = null

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val found = skeleton.find(
                    event.x,
                    event.y
                ) ?: return false

                onSelectPoint?.onSelectPoint(
                    found
                )
            }
        }

        return true
    }

}