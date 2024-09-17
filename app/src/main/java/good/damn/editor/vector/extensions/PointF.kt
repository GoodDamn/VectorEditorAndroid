package good.damn.editor.vector.extensions

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