package good.damn.sav.core.animation.keyframe

import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.readU
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import java.io.InputStream
import java.io.OutputStream

data class VEMKeyframeStrokeWidth(
    override val factor: Float,
    val strokeWidth: Float
): VEIKeyframe {

    companion object {
        fun import(
            size: Size,
            factor: Float,
            inp: InputStream
        ) = inp.run {
            VEMKeyframeStrokeWidth(
                factor,
                readU() * size.width,
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
            strokeWidth.toDigitalFraction(
                canvasSize.width
            )
        )
    }

}