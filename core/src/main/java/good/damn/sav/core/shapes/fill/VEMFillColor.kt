package good.damn.sav.core.shapes.fill

import android.graphics.Paint
import androidx.annotation.ColorInt
import good.damn.sav.core.animation.interpolators.fill.VEMFillColorPriority
import good.damn.sav.core.animation.keyframe.VEIInterpolatablePriority
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.interpolate
import good.damn.sav.misc.extensions.toInt32
import java.io.OutputStream

data class VEMFillColor(
    @setparam:ColorInt
    @get:ColorInt
    var color: ByteArray
): VEIFill {
    companion object {
        const val TYPE = 0
    }

    override fun fillPaint(
        paint: Paint
    ) {
        paint.shader = null
        paint.color = color.toInt32()
    }

    override fun write(
        os: OutputStream,
        s: Size
    ) = os.run {
        write(TYPE)
        write(color)
    }

    override fun copy() = VEMFillColor(
        color.clone()
    )

    override fun createPriority() = VEMFillColorPriority(
        this
    )

    override fun equals(
        other: Any?
    ): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VEMFillColor

        return color.contentEquals(other.color)
    }

    override fun hashCode() = color.contentHashCode()
}