package good.damn.sav.core.shapes

import android.graphics.Canvas
import android.graphics.Paint
import good.damn.sav.core.points.VEPointIndexed
import java.io.InputStream
import java.io.OutputStream

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

    init {
        mPaint.apply {
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
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