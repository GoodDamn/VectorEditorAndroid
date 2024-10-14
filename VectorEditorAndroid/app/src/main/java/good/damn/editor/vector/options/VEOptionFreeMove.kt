package good.damn.editor.vector.options

import android.graphics.Canvas
import android.view.MotionEvent
import good.damn.editor.vector.interfaces.VEIDrawable
import good.damn.editor.vector.interfaces.VEITouchable
import good.damn.editor.vector.skeleton.VESkeleton2D

class VEOptionFreeMove
: VEITouchable {

    var skeleton: VESkeleton2D? = null

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        return true
    }

}