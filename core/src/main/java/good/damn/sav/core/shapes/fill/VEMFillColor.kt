package good.damn.sav.core.shapes.fill

import android.graphics.Paint
import android.graphics.PointF
import androidx.annotation.ColorInt
import good.damn.sav.core.animation.keyframe.VEIInterpolatablePriority
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.interpolate
import good.damn.sav.misc.extensions.io.readFraction
import good.damn.sav.misc.extensions.io.readInt32
import good.damn.sav.misc.extensions.io.readU
import good.damn.sav.misc.extensions.primitives.toByteArray
import good.damn.sav.misc.extensions.toInt32
import java.io.InputStream
import java.io.OutputStream

data class VEMFillColor(
    @setparam:ColorInt
    @get:ColorInt
    var color: ByteArray
): VEIFill,
VEIInterpolatablePriority {
    companion object {
        const val TYPE = 0
    }

    override val priority = 0

    private lateinit var mInterpolatedColor: VEMFillColor

    override fun startInterpolate() {
        mInterpolatedColor = VEMFillColor(
            ByteArray(4)
        )
    }

    override fun priorityInterpolate(
        factor: Float,
        start: VEIInterpolatablePriority,
        end: VEIInterpolatablePriority
    ): VEIFill {
        mInterpolatedColor.color.interpolate(
            start.nextInterpolateValue(0),
            end.nextInterpolateValue(0),
            factor
        )
        return mInterpolatedColor
    }

    override fun nextInterpolateValue(
        index: Int
    ) = color

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