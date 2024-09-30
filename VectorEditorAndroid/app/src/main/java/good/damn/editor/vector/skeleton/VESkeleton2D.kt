package good.damn.editor.vector.skeleton

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
import good.damn.editor.vector.extensions.drawCircle
import good.damn.editor.vector.interfaces.VEIDrawable
import java.util.LinkedList
import kotlin.math.abs
import kotlin.math.hypot

class VESkeleton2D
: VEIDrawable {

    companion object {
        private const val TAG = "VESkeleton2D"
    }

    private val mPaintPoint = Paint().apply {
        color = 0x55ffffff
    }

    private val mPoints = LinkedList<
        PointF
    >()

    private val mRadius = 50f

    fun getLastPoint() = mPoints
        .lastOrNull()

    fun find(
        withX: Float,
        withY: Float
    ): PointF? {
        mPoints.forEach {
            if (abs(hypot(
                it.x - withX,
                it.y - withY
            )) < mRadius) {
                return it
            }
        }

        return null
    }

    fun addSkeletonPoint(
        point: PointF
    ) {
        mPoints.add(
            point
        )
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        mPoints.forEach {
            canvas.drawCircle(
                it,
                mRadius,
                mPaintPoint
            )
        }
    }
}