package good.damn.editor.vector.extensions

import android.graphics.PointF
import good.damn.editor.vector.points.VEPointIndexed
import java.io.OutputStream

inline fun VEPointIndexed.writeToStreamIndexed(
    os: OutputStream
) {
    os.write(
        index
    )
}

inline fun VEPointIndexed.interpolate(
    f: Float,
    point2: VEPointIndexed?
) = if (point2 == null) {
    null
} else VEPointIndexed(
    (x + point2.x) * f,
    (y + point2.y) * f
)