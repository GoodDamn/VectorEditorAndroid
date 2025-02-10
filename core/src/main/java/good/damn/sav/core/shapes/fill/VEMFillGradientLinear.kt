package good.damn.sav.core.shapes.fill

import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Shader

class VEMFillGradientLinear(
    val p0: PointF,
    val p1: PointF,
    val colors: IntArray,
    val positions: FloatArray
): VEIFill {
    override fun fillPaint(
        paint: Paint
    ) {
        paint.shader = LinearGradient(
            p0.x,
            p0.y,
            p1.x,
            p1.y,
            colors,
            positions,
            Shader.TileMode.CLAMP
        )
    }

    override fun toByteArray() = byteArrayOf()
}