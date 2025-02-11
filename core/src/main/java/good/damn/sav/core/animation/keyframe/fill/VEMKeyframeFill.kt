package good.damn.sav.core.animation.keyframe.fill

import android.graphics.PointF
import good.damn.sav.core.animation.keyframe.VEIKeyframe
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.core.shapes.fill.VEMFillColor
import good.damn.sav.core.shapes.fill.VEMFillGradientLinear
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.readFraction
import good.damn.sav.misc.extensions.io.readInt32
import good.damn.sav.misc.extensions.io.readU
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import java.io.InputStream
import java.io.OutputStream

open class VEMKeyframeFill(
    override val factor: Float,
    val fill: VEIFill
): VEIKeyframe {

    companion object {
        fun import(
            factor: Float,
            canvasSize: Size,
            buffer4: ByteArray,
            os: InputStream
        ) = VEMKeyframeFill(
            factor,
            VEMFillColor.import(
                os, buffer4, canvasSize
            )
        )
    }

    final override fun export(
        os: OutputStream,
        canvasSize: Size
    ) = os.run {
        write(
            factor.toDigitalFraction()
        )

        fill.write(
            this,
            canvasSize
        )
    }
}