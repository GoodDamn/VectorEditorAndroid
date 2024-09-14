package good.damn.editor.vector.extensions

import android.graphics.RectF
import good.damn.editor.vector.extensions.io.write
import good.damn.editor.vector.extensions.primitives.toDigitalFraction
import java.io.OutputStream

fun RectF.writeToStream(
    out: OutputStream,
    width: Float,
    height: Float
) = out.run {
    write(
        left.toDigitalFraction(
            width
        )
    )
    write(
        top.toDigitalFraction(
            height
        )
    )

    write(
        right.toDigitalFraction(
            width
        )
    )

    write(
        bottom.toDigitalFraction(
            height
        )
    )
}