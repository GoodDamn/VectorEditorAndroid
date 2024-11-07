package good.damn.sav.core.shapes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
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
        val p = points[0]
            ?: return false

        val pp = points[1]
            ?: return false

        mPointLeftTop.set(
            p.x - mPaint.strokeWidth,
            p.y
        )

        mPointRightTop.set(
            p.x + mPaint.strokeWidth,
            p.y
        )

        mPointLeftBottom.set(
            pp.x - mPaint.strokeWidth,
            pp.y
        )

        mPointRightBottom.set(
            pp.x + mPaint.strokeWidth,
            pp.y
        )

        mPaintDebug.strokeWidth.let {
            val x1 = mPointLeftTop.x
            val x6 = mPointRightBottom.x

            if (x < x1 || x > x6 || y < p.y || y > pp.y) {
                return false
            }

            Log.d(TAG, "checkHit: $x1 < $x < ${mPointLeftTop.y};")

            if (x1 < x && x < mPointLeftTop.y) {
                return false
            }

            Log.d(TAG, "checkHit2: ${mPointRightTop.y} < $x < $x6")

            return !(mPointRightTop.y < x && x < x6)
        }
    }

    override fun draw(
        canvas: Canvas
    ) = canvas.run {
        points[0]?.let { p1 ->
            points[1]?.let { p2 ->
                drawLine(
                    p1.x,
                    p1.y,
                    p2.x,
                    p2.y,
                    mPaint
                )

                drawLine(
                    mPointLeftTop,
                    mPointLeftBottom,
                    mPaintDebug
                )

                drawLine(
                    mPointRightTop,
                    mPointRightBottom,
                    mPaintDebug
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