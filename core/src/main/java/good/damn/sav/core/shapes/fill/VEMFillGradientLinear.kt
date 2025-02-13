package good.damn.sav.core.shapes.fill

import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import good.damn.sav.core.animation.interpolators.fill.VEMFillGradientPriority
import good.damn.sav.core.animation.keyframe.VEIInterpolatablePriority
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.interpolate
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toByteArray
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import good.damn.sav.misc.extensions.toInt32
import good.damn.sav.misc.extensions.writeToStream
import good.damn.sav.misc.utils.VEUtilsWrite
import java.io.OutputStream

data class VEMFillGradientLinear(
    val p0x: Float,
    val p0y: Float,
    val p1x: Float,
    val p1y: Float,
    val colors: IntArray,
    val positions: FloatArray
): VEIFill {

    companion object {
        const val TYPE = 1
    }

    private val gradient = LinearGradient(
        p0x,
        p0y,
        p1x,
        p1y,
        colors,
        positions,
        Shader.TileMode.CLAMP
    )

    override fun createPriority() = VEMFillGradientPriority(
        this
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

        VEUtilsWrite.xy(
            p0x,
            p0y,
            s,
            this
        )

        VEUtilsWrite.xy(
            p1x,
            p1y,
            s,
            this
        )

        write(
            colors.size
        )

        colors.forEach {
            write(it.toByteArray())
        }

        positions.forEach {
            write(
                it.toDigitalFraction()
            )
        }
    }
}