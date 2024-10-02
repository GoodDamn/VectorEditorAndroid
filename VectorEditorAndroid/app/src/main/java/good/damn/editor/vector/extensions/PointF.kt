package good.damn.editor.vector.extensions

import android.graphics.Point
import android.graphics.PointF
import good.damn.editor.vector.extensions.io.write
import good.damn.editor.vector.extensions.primitives.toDigitalFraction
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
    point2: PointF?
) = if (point2 == null) {
    null
} else PointF(
    (x + point2.x) * f,
    (y + point2.y) * f
)