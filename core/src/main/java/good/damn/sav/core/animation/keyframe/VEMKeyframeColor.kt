package good.damn.sav.core.animation.keyframe

import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.readInt32
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toByteArray
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import java.io.InputStream
import java.io.OutputStream

data class VEMKeyframeColor(
    override val factor: Float,
    val color: ByteArray
): VEIKeyframe {

    companion object {
        fun import(
            fraction: Float,
            inp: InputStream
        ) = inp.run {
            val color = ByteArray(4)
            inp.readInt32(color)

            VEMKeyframeColor(
                fraction,
                color
            )
        }
    }

    override fun export(
        os: OutputStream,
        canvasSize: Size
    ) = os.run {
        write(
            factor.toDigitalFraction()
        )

        write(
            color
        )
    }
}