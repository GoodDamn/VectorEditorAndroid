package good.damn.sav.core.shapes.primitives

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.utils.VEUtilsHit
import good.damn.sav.misc.extensions.lineTo
import good.damn.sav.misc.extensions.moveTo

class VEShapeFill
: VEShapeBase() {

    companion object {
        const val shapeType = 1
    }

    override val points = ArrayList<
        VEPointIndexed?
    >()

    private val mPath = Path()

    init {
        mPaint.style = Paint.Style.FILL
    }

    override fun shapeType() = shapeType

    override fun newInstance(
        width: Float,
        height: Float
    ) = VEShapeFill().apply {
        fill = this@VEShapeFill.fill
    }

    override fun draw(
        canvas: Canvas
    ) {
        if (points.size < 3) {
            return
        }

        mPath.reset()

        val iterator = points.iterator()

        iterator.next()?.apply {
            mPath.moveTo(
                this
            )
        }

        while (iterator.hasNext()) {
            iterator.next()?.apply {
                mPath.lineTo(
                    this
                )
            }
        }

        canvas.drawPath(
            mPath,
            mPaint
        )
    }

    override fun checkHit(
        x: Float,
        y: Float
    ) = VEUtilsHit.poly(
        x, y,
        points
    )

}