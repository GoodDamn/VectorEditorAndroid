package good.damn.sav.misc.extensions

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

inline fun Canvas.drawLine(
    point1: PointF,
    point2: PointF,
    paint: Paint
) {
    drawLine(
        point1.x,
        point1.y,
        point2.x,
        point2.y,
        paint
    )
}

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