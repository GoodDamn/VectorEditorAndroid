package good.damn.sav.core.shapes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.misc.extensions.lineTo
import good.damn.sav.misc.extensions.moveTo
import java.util.LinkedList

class VEShapeFill(
    canvasWidth: Float,
    canvasHeight: Float
): VEShapeBase(
    canvasWidth,
    canvasHeight
) {

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
    ) = VEShapeFill(
        width,
        height
    ).apply {
        color = this@VEShapeFill.color
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
    ): Boolean {
        if (points.size < 3) {
            return false
        }

        var i = 0
        var j = points.size-1
        var isInside = false

        var pi: VEPointIndexed
        var pj: VEPointIndexed

        while (i < points.size) {
            pi = points[i]
                ?: continue

            pj = points[j]
                ?: continue

            if (
                ((pi.y > y) != (pj.y > y)) &&
                (x < (pj.x - pi.x) * (y - pi.y) / (pj.y - pi.y) + pi.x)
            ) {
                isInside = !isInside
            }

            j = i++
        }

        return isInside
    }

}