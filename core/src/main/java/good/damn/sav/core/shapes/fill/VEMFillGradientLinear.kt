package good.damn.sav.core.shapes.fill

import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Shader

class VEMFillGradientLinear(
    p0: PointF,
    p1: PointF,
    colors: IntArray,
    positions: FloatArray
): VEIFill {

    private val gradient = LinearGradient(
        p0.x,
        p0.y,
        p1.x,
        p1.y,
        colors,
        positions,
        Shader.TileMode.CLAMP
    )

    override fun fillPaint(
        paint: Paint
    ) {
        paint.shader = gradient
    }

    override fun toByteArray() = byteArrayOf()
}