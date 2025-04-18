package good.damn.sav.core.shapes.primitives

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PointF
import good.damn.sav.core.VEMProjection
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.utils.VEUtilsHit
import good.damn.sav.misc.extensions.moveTo
import good.damn.sav.misc.extensions.quadTo

class VEShapeBezierQuad
: VEShapeBase() {

    companion object {
        private val TAG = VEShapeBezierQuad::class.simpleName
        const val shapeType = 2

        private const val STEP_HIT = 1.0f / 15
    }

    private val mPath = Path()

    override val points = ArrayList<
        VEPointIndexed?
    >(3).apply {
        add(null)
        add(null)
        add(null)
    }

    private val mPaintDebug = Paint().apply {
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
        color = 0xffffff00.toInt()
    }

    private val mTempPointHitFrom = PointF()
    private val mTempPointHitTo = PointF()

    init {
        mPaint.apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
    }


    override fun shapeType() = shapeType

    override fun draw(
        canvas: Canvas
    ) = mPath.run {
        reset()

        val p0 = points[0]
            ?: return@run

        val p1 = points[1]
            ?: return@run

        val p2 = points[2]
            ?: return@run

        moveTo(p0)

        quadTo(
            p1,
            p2
        )

        canvas.drawPath(
            this,
            mPaint
        )

    }

    override fun checkHit(
        x: Float,
        y: Float,
        projection: VEMProjection
    ): Boolean {

        val p0 = points[0]
            ?: return false

        val p1 = points[1]
            ?: return false

        val p2 = points[2]
            ?: return false

        var i = STEP_HIT

        mTempPointHitFrom.set(
            p0.x,
            p0.y
        )

        var lineWidth = mPaint.strokeWidth * projection.scale * 0.5f
        lineWidth = if (
            lineWidth > projection.radiusPointsScaled
        ) lineWidth else projection.radiusPointsScaled

        while (i < 1.01f) {
            val lp1x = p0.x + (p1.x - p0.x) * i
            val lp1y = p0.y + (p1.y - p0.y) * i

            val lp2x = p1.x + (p2.x - p1.x) * i
            val lp2y = p1.y + (p2.y - p1.y) * i

            mTempPointHitTo.set(
                lp1x + (lp2x - lp1x) * i,
                lp1y + (lp2y - lp1y) * i
            )

            if (VEUtilsHit.line(
                x, y,
                mTempPointHitFrom,
                mTempPointHitTo,
                lineWidth
            )) {
                return true
            }

            mTempPointHitFrom.set(
                mTempPointHitTo
            )

            i += STEP_HIT
        }


        return false
    }

    override fun newInstance(
        width: Float,
        height: Float
    ) = VEShapeBezierQuad()

}