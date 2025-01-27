package good.damn.sav.core.animation.keyframe

import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import java.io.OutputStream

data class VEMKeyframeColor(
    override val factor: Float,
    val color: ByteArray
): VEIKeyframe {

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