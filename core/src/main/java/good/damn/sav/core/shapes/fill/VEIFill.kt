package good.damn.sav.core.shapes.fill

import android.graphics.Paint
import good.damn.sav.core.animation.keyframe.VEIInterpolatablePriority
import good.damn.sav.misc.Size
import java.io.OutputStream

interface VEIFill {

    fun fillPaint(
        paint: Paint
    )

    fun createPriority(): VEIInterpolatablePriority

    fun write(
        os: OutputStream,
        s: Size
    )
}