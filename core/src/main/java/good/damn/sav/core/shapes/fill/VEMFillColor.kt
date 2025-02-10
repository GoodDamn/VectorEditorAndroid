package good.damn.sav.core.shapes.fill

import android.graphics.Paint
import androidx.annotation.ColorInt
import good.damn.sav.misc.extensions.primitives.toByteArray

class VEMFillColor(
    @setparam:ColorInt
    @get:ColorInt
    var color: Int
): VEIFill {
    override fun fillPaint(
        paint: Paint
    ) {
        paint.color = color
    }

    override fun toByteArray(): ByteArray = color.toByteArray()
}