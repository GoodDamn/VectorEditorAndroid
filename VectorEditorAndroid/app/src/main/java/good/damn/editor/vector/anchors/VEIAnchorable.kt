package good.damn.editor.vector.anchors

import android.graphics.Canvas
import android.graphics.PointF

interface VEIAnchorable {
    fun onDraw(
        canvas: Canvas,
        x: Float,
        y: Float,
        x2: Float,
        y2: Float
    )

    fun checkAnchor(
        x: Float,
        y: Float,
        x2: Float,
        y2: Float
    ): Boolean
}