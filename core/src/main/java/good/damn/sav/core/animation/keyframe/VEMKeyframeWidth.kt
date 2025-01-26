package good.damn.sav.core.animation.keyframe

import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import java.io.OutputStream

data class VEMKeyframeWidth(
    override val factor: Float,
    val strokeWidth: Float
): VEIKeyframe {

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