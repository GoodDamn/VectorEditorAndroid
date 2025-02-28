package good.damn.sav.core.shapes.fill

import android.graphics.Paint
import good.damn.sav.core.VEIIdentifiable
import good.damn.sav.core.animation.keyframe.VEIInterpolatablePriority
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.readFraction
import good.damn.sav.misc.extensions.io.readInt32
import good.damn.sav.misc.extensions.io.readU
import java.io.InputStream
import java.io.OutputStream

interface VEIFill
: VEIIdentifiable {

    companion object {
        fun importFill(
            inp: InputStream,
            canvasSize: Size
        ) = inp.run {
            when (readU()) {
                VEMFillGradientLinear.TYPE -> {
                    val p0x = readFraction() * canvasSize.width
                    val p0y = readFraction() * canvasSize.height

                    val p1x = readFraction() * canvasSize.width
                    val p1y = readFraction() * canvasSize.height

                    val s = readU()

                    VEMFillGradientLinear(
                        p0x,
                        p0y,
                        p1x,
                        p1y,
                        IntArray(s).apply {
                            for (ic in indices) {
                                this[ic] = readInt32(
                                    ByteArray(4)
                                )
                            }
                        },
                        FloatArray(s).apply {
                            for (ic in indices) {
                                this[ic] = readFraction()
                            }
                        }
                    )
                }

                else -> {
                    val b = ByteArray(4)
                    readInt32(b)
                    VEMFillColor(b)
                }
            }
        }
    }

    fun fillPaint(
        paint: Paint
    )

    fun createPriority(): VEIInterpolatablePriority

    fun copy(): VEIFill

    fun write(
        os: OutputStream,
        s: Size
    )
}