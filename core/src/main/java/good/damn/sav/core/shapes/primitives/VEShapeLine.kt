package good.damn.sav.core.shapes.primitives

import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.utils.VEUtilsHit
import good.damn.sav.misc.extensions.drawLine

class VEShapeLine
: VEShapeBase() {
    companion object {
        private val TAG = VEShapeLine::class.simpleName
    }

    override val points = ArrayList<
        VEPointIndexed?
    >(2).apply {
        add(null)
        add(null)
    }

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

    override fun shapeType() = 0

    override fun checkHit(
        x: Float,
        y: Float
    ): Boolean {
        val p = points[0]
            ?: return false

        val pp = points[1]
            ?: return false

        val stroke = mPaint.strokeWidth * 0.5f

        return VEUtilsHit.line(
            x, y,
            p, pp,
            if (
                stroke > 50f
            ) stroke else 50f
        )
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

                /*drawLine(
                    mPointLeftTop,
                    mPointLeftBottom,
                    mPaintDebug
                )

                drawLine(
                    mPointRightTop,
                    mPointRightBottom,
                    mPaintDebug
                )*/
            }
        }

        Unit
    }


    override fun newInstance(
        width: Float,
        height: Float
    ) = VEShapeLine()
}