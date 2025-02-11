package good.damn.sav.core.shapes.fill

import android.graphics.Paint
import androidx.annotation.ColorInt
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.primitives.toByteArray
import java.io.OutputStream

data class VEMFillColor(
    @setparam:ColorInt
    @get:ColorInt
    var color: Int
): VEIFill {

    companion object {
        const val TYPE = 0
    }

    override fun fillPaint(
        paint: Paint
    ) {
        paint.shader = null
        paint.color = color
    }

    override fun write(
        os: OutputStream,
        s: Size
    ) = os.run {
        write(TYPE)
        write(color.toByteArray())
    }
}