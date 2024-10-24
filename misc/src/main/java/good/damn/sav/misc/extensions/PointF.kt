package good.damn.sav.misc.extensions

import android.graphics.PointF
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import java.io.OutputStream

inline fun PointF.writeToStream(
    out: OutputStream,
    width: Float,
    height: Float
) = out.run {
    write(
        x.toDigitalFraction(
            width
        )
    )

    write(
        y.toDigitalFraction(
            height
        )
    )
}

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