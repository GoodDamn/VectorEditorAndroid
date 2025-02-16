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
    var p0x: Float,
    var p0y: Float,
    var p1x: Float,
    var p1y: Float,
    var colors: IntArray,
    var positions: FloatArray
): VEIFill {

    companion object {
        const val TYPE = 1
    }

    var gradient = LinearGradient(
        p0x,
        p0y,
        p1x,
        p1y,
        colors,
        positions,
        Shader.TileMode.CLAMP
    )

    fun updateGradient(
        p0x: Float,
        p0y: Float,
        p1x: Float,
        p1y: Float,
        colors: IntArray,
        positions: FloatArray,
        gradient: LinearGradient
    ) {
        this.p0x = p0x
        this.p0y = p0y
        this.p1x = p1x
        this.p1y = p1y
        this.colors = colors
        this.positions = positions
        this.gradient = gradient
    }

    override fun copy() = VEMFillGradientLinear(
        p0x, p0y,
        p1x, p1y,
        colors.clone(),
        positions.clone()
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