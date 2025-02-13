package good.damn.sav.misc.utils

import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import java.io.OutputStream

class VEUtilsWrite {
    companion object {
        inline fun xy(
            x: Float,
            y: Float,
            size: Size,
            os: OutputStream
        ) = os.run {
            write(
                x.toDigitalFraction(
                    size.width
                )
            )

            write(
                y.toDigitalFraction(
                    size.height
                )
            )
        }
    }
}