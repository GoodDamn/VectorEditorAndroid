package good.damn.editor.vector.extensions

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

inline fun Canvas.drawCircle(
    point: PointF,
    radius: Float,
    paint: Paint
) {
    drawCircle(
        point.x,
        point.y,
        radius,
        paint
    )
}