package good.damn.sav.core.animation.keyframe

import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.readFraction
import good.damn.sav.misc.extensions.io.readU
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import java.io.InputStream
import java.io.OutputStream

data class VEMKeyframePosition(
    override val factor: Float,
    val x: Float,
    val y: Float
): VEIKeyframe {

    companion object {
        fun import(
            size: Size,
            factor: Float,
            inp: InputStream
        ) = inp.run {
            VEMKeyframePosition(
                factor,
                readFraction() * size.width,
                readFraction() * size.height
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
            x.toDigitalFraction(
                canvasSize.width
            )
        )

        write(
            y.toDigitalFraction(
                canvasSize.height
            )
        )
    }

}