package good.damn.editor.vector.options

import android.graphics.Canvas
import android.view.MotionEvent
import good.damn.editor.vector.lists.VEListShapes
import good.damn.editor.vector.points.VEPointIndexed
import good.damn.editor.vector.skeleton.VESkeleton2D

class VEOptionFreeMove
: VEIOptionable {

    override fun onDraw(
        canvas: Canvas
    ) = Unit

    override fun onTouchEvent(
        event: MotionEvent
    ) = true

}