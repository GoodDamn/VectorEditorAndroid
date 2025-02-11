package good.damn.sav.core.shapes.fill

import android.graphics.Paint
import android.graphics.PointF
import androidx.annotation.ColorInt
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.readFraction
import good.damn.sav.misc.extensions.io.readInt32
import good.damn.sav.misc.extensions.io.readU
import good.damn.sav.misc.extensions.primitives.toByteArray
import java.io.InputStream
import java.io.OutputStream

data class VEMFillColor(
    @setparam:ColorInt
    @get:ColorInt
    var color: Int
): VEIFill {

    companion object {
        const val TYPE = 0

        fun import(
            os: InputStream,
            buffer4: ByteArray,
            canvasSize: Size
        ) = os.run {
            when (readU()) {
                VEMFillGradientLinear.TYPE -> {
                    val p = PointF(
                        readFraction() * canvasSize.width,
                        readFraction() * canvasSize.height
                    )

                    val pp = PointF(
                        readFraction() * canvasSize.width,
                        readFraction() * canvasSize.height
                    )

                    val s = readU()

                    val colors = IntArray(s).apply {
                        for (ic in indices) {
                            this[ic] = readInt32(buffer4)
                        }
                    }

                    val positions = FloatArray(s).apply {
                        for (ic in indices) {
                            this[ic] = readFraction()
                        }
                    }

                    VEMFillGradientLinear(
                        p,
                        pp,
                        colors,
                        positions
                    )
                }
                else -> VEMFillColor(
                    readInt32(buffer4)
                )
            }
        }

    }

    override fun fillPaint(
        paint: Paint
    ) {
        paint.shader = null
        paint.color = color
    }

    override fun write(
        os: OutputStream,
        s: Size
    ) = os.run {
        write(TYPE)
        write(color.toByteArray())
    }
}