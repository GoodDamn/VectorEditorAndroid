package good.damn.sav.core.shapes.fill

import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Shader
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toByteArray
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import good.damn.sav.misc.extensions.writeToStream
import java.io.OutputStream

data class VEMFillGradientLinear(
    private val p0: PointF,
    private val p1: PointF,
    private val colors: IntArray,
    private val positions: FloatArray
): VEIFill {

    companion object {
        const val TYPE = 1
    }

    private val gradient = LinearGradient(
        p0.x,
        p0.y,
        p1.x,
        p1.y,
        colors,
        positions,
        Shader.TileMode.CLAMP
    )

    override fun fillPaint(
        paint: Paint
    ) {
        paint.shader = gradient
    }

    override fun write(
        os: OutputStream,
        s: Size
    ) = os.run {
        write(TYPE)
        p0.writeToStream(
            this,
            s.width,
            s.height
        )

        p1.writeToStream(
            this,
            s.width,
            s.height
        )

        write(
            colors.size
        )

        colors.forEach {
            write(
                it.toByteArray()
            )
        }

        positions.forEach {
            write(
                it.toDigitalFraction()
            )
        }
    }
}