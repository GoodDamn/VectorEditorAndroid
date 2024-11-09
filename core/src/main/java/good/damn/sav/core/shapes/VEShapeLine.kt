package good.damn.sav.core.shapes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.misc.extensions.angle
import good.damn.sav.misc.extensions.drawLine
import good.damn.sav.misc.extensions.length
import good.damn.sav.misc.extensions.minMax
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.cos
import kotlin.math.log
import kotlin.math.sin

class VEShapeLine(
    canvasWidth: Float,
    canvasHeight: Float
): VEShapeBase(
    canvasWidth,
    canvasHeight
) {
    companion object {
        const val ENCODE_TYPE = 0
        private val TAG = VEShapeLine::class.simpleName
    }

    override val points = Array<
        VEPointIndexed?
    >(2) { null }

    private val mPointLeftTop = PointF()
    private val mPointLeftBottom = PointF()
    private val mPointRightTop = PointF()
    private val mPointRightBottom = PointF()

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
        Log.d(TAG, "checkHit: $this")
        val p = points[0]
            ?: return false

        val pp = points[1]
            ?: return false

        val angle = p.angle(pp)
        val stroke = mPaint.strokeWidth * 0.5f
        val m = if (
            stroke > 25f
        ) stroke else 25f

        val sin = m * sin(angle)
        val cos = m * -cos(angle)

        mPointLeftTop.set(
            p.x + cos,
            p.y + sin
        )

        mPointLeftBottom.set(
            pp.x + cos,
            pp.y + sin
        )

        mPointRightTop.set(
            p.x + -cos,
            p.y + -sin
        )

        mPointRightBottom.set(
            pp.x + -cos,
            pp.y + -sin
        )

        val minMaxX = minMax(
            mPointLeftTop.x,
            mPointLeftBottom.x,
            mPointRightTop.x,
            mPointRightBottom.x
        )

        val minMaxY = minMax(
            mPointLeftTop.y,
            mPointLeftBottom.y,
            mPointRightTop.y,
            mPointRightBottom.y
        )

        if (
            x < minMaxX.first ||
            x > minMaxX.second ||
            y < minMaxY.first ||
            y > minMaxY.second
        ) {
            return false
        }


        var dpp = pp.x - p.x
        if (dpp == 0.0f) {
            dpp = 0.01f
        }
        val k = (pp.y - p.y) / dpp


        val y1 = k * (x - mPointLeftTop.x) + mPointLeftTop.y
        val y2 = k * (x - mPointRightTop.x) + mPointRightTop.y
        
        return (y1 > y && y > y2) || (y1 < y && y < y2)
    }

    override fun draw(
        canvas: Canvas
    ) = canvas.run {
        points[0]?.let { p ->
            points[1]?.let { pp ->
                drawLine(
                    p,
                    pp,
                    mPaint
                )
            }
        }

        Unit
    }


    override fun newInstance(
        width: Float,
        height: Float
    ) = VEShapeLine(
        width,
        height
    )
}