package good.damn.sav.misc.extensions

import android.graphics.PointF
import good.damn.sav.misc.Size
import good.damn.sav.misc.utils.VEUtilsWrite
import java.io.OutputStream
import kotlin.math.atan2
import kotlin.math.hypot

inline fun PointF.length(
    with: PointF
) = hypot(
    with.x - x,
    with.y - y
)

inline fun PointF.angle(
    with: PointF
) = atan2(
    with.x - x,
    with.y - y
)

inline fun PointF.writeToStream(
    out: OutputStream,
    size: Size
) = VEUtilsWrite.xy(
    x,
    y,
    size,
    out
)

inline fun PointF.interpolate(
    f: Float,
    p: PointF,
    p2: PointF
) {
    x = (p.x + p2.x) * f
    y = (p.y + p2.y) * f
}

inline fun PointF.interpolateWith(
    f: Float,
    point2: PointF?
) = if (point2 == null) {
    null
} else PointF(
    (x + point2.x) * f,
    (y + point2.y) * f
)