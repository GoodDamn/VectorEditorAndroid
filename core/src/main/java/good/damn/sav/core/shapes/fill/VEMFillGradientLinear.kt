package good.damn.sav.core.shapes.fill

import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Shader
import android.util.Log
import good.damn.sav.core.animation.keyframe.VEIInterpolatablePriority
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.interpolate
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toByteArray
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import good.damn.sav.misc.extensions.toInt32
import good.damn.sav.misc.extensions.writeToStream
import java.io.OutputStream

class VEMFillGradientLinear(
    p: PointF,
    pp: PointF,
    colors: IntArray,
    val positions: FloatArray
): VEIFill,
VEIInterpolatablePriority {

    companion object {
        const val TYPE = 1
    }

    override val priority = 1

    val p0 = PointF(p.x, p.y)
    val p1 = PointF(pp.x, pp.y)

    private val gradient = LinearGradient(
        p0.x,
        p0.y,
        p1.x,
        p1.y,
        colors,
        positions,
        Shader.TileMode.CLAMP
    )

    private val mColorBytes = colors.map {
        it.toByteArray()
    }

    private val mInterpolatedColors = IntArray(
        colors.size
    )

    private lateinit var mInterpolatedColor: ByteArray

    override fun startInterpolate() {
        mInterpolatedColor = ByteArray(4)
    }

    override fun priorityInterpolate(
        factor: Float,
        start: VEIInterpolatablePriority,
        end: VEIInterpolatablePriority
    ): VEIFill {
        for (i in mColorBytes.indices) {
            mInterpolatedColor.interpolate(
                start.nextInterpolateValue(i),
                end.nextInterpolateValue(i),
                factor
            )
            mInterpolatedColors[i] = mInterpolatedColor.toInt32()
        }

        return VEMFillGradientLinear(
            p0,
            p1,
            mInterpolatedColors,
            positions
        )
    }

    override fun nextInterpolateValue(
        index: Int
    ) = mColorBytes[index]

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
            mColorBytes.size
        )

        mColorBytes.forEach {
            write(it)
        }

        positions.forEach {
            write(
                it.toDigitalFraction()
            )
        }
    }
}