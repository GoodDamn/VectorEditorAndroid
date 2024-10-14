package good.damn.editor.vector.options

import android.graphics.Canvas
import android.graphics.PointF
import android.view.MotionEvent
import good.damn.editor.vector.lists.VEListShapes
import good.damn.editor.vector.points.VEPointIndexed
import good.damn.editor.vector.skeleton.VESkeleton2D

interface VEIOptionable {
    fun onTouchEvent(
        event: MotionEvent
    ): Boolean

    fun onDraw(
        canvas: Canvas
    )
}