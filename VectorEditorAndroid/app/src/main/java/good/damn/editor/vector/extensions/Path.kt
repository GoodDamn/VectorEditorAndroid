package good.damn.editor.vector.extensions

import android.graphics.Path
import android.graphics.Point
import android.graphics.PointF

inline fun Path.moveTo(
    point: PointF
) {
    moveTo(
        point.x,
        point.y
    )
}

inline fun Path.cubicTo(
    point1: PointF,
    point2: PointF,
    point3: PointF
) {

    cubicTo(
        point1.x,
        point1.y,
        point2.x,
        point2.y,
        point3.x,
        point3.y
    )
}