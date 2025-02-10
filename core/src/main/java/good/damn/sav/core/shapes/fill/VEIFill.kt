package good.damn.sav.core.shapes.fill

import android.graphics.Paint

interface VEIFill {
    fun fillPaint(
        paint: Paint
    )

    fun toByteArray(): ByteArray
}