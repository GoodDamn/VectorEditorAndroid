package good.damn.sav.core.shapes

import android.graphics.Canvas
import android.graphics.Paint
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.misc.extensions.drawLine
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.max
import kotlin.math.min

class VEShapeLine(
    canvasWidth: Float,
    canvasHeight: Float
): VEShapeBase(
    canvasWidth,
    canvasHeight
) {
    companion object {
        const val ENCODE_TYPE = 0
    }

    override val points = Array<
        VEPointIndexed?
    >(2) { null }

    private val mPaintDebug = Paint().apply {
        color = 0xffffff00.toInt()
        style = Paint.Style.STROKE
        strokeWidth = 10f
    }

    init {
        mPaint.apply {
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }
    }

    override fun checkHit(
        x: Float,
        y: Float
    ): Boolean {
        val p = points[0]
            ?: return false

        val pp = points[1]
            ?: return false

        mPaintDebug.strokeWidth.let {
            val x1 = p.x - it
            val x6 = pp.x + it

            if (x < x1 || x > x6 || y < p.y || y > pp.y) {
                return false
            }

            if (x1 < x && x < pp.y) {
                return false
            }

            if (p.y < x && x < x6) {
                return false
            }

            return true
        }
    }

    override fun draw(
        canvas: Canvas
    ) {
        points[0]?.let { p1 ->
            points[1]?.let { p2 ->
                canvas.drawLine(
                    p1.x,
                    p1.y,
                    p2.x,
                    p2.y,
                    mPaint
                )

                canvas.drawLine(
                    p1,
                    p2,
                    mPaintDebug
                )


            }
        }
    }


    override fun newInstance(
        width: Float,
        height: Float
    ) = VEShapeLine(
        width,
        height
    )
}