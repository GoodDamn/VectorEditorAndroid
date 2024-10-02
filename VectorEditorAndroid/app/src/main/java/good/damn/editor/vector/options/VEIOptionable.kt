package good.damn.editor.vector.options

import android.graphics.Canvas
import android.graphics.PointF
import good.damn.editor.vector.lists.VEListShapes
import good.damn.editor.vector.skeleton.VESkeleton2D

interface VEIOptionable {
    fun runOption(
        shapes: VEListShapes,
        selectedPoint: PointF?,
        skeleton: VESkeleton2D
    )

    fun onDraw(
        canvas: Canvas
    )
}