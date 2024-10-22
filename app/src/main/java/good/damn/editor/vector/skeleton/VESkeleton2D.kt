package good.damn.editor.vector.skeleton

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import good.damn.editor.vector.extensions.drawCircle
import good.damn.editor.vector.interfaces.VEIDrawable
import good.damn.editor.vector.actions.callbacks.VEICallbackOnAddSkeletonPoint
import good.damn.editor.vector.points.VEPointIndexed
import good.damn.editor.vector.structures.BinaryTree
import kotlin.math.abs
import kotlin.math.hypot

class VESkeleton2D(
    dataStruct: MutableList<VEPointIndexed>
): VEIDrawable {

    companion object {
        private const val TAG = "VESkeleton2D"
    }

    var onAddSkeletonPoint: VEICallbackOnAddSkeletonPoint? = null

    val size: Int
        get() = mPoints.size

    val points: MutableList<VEPointIndexed>
        get() = mPoints

    private val mPaintPoint = Paint().apply {
        color = 0x55ffffff
    }

    private val mPoints = dataStruct

    val radius = 50f

    fun getLastPoint() = mPoints
        .lastOrNull()

    fun find(
        withX: Float,
        withY: Float
    ): VEPointIndexed? {
        mPoints.forEach {
            if (abs(hypot(
                it.x - withX,
                it.y - withY
            )) < radius) {
                return it
            }
        }

        return null
    }

    fun getPointIndexed(
        i: Int
    ) = mPoints[i]

    fun addSkeletonPoint(
        point: VEPointIndexed
    ) {
        point.index = size
        mPoints.add(
            point
        )
        onAddSkeletonPoint?.onAddSkeletonPoint(
            point
        )
    }

    fun resetSkeleton(
        points: MutableList<VEPointIndexed>
    ) = mPoints.run {
        clear()
        addAll(points)
    }

    fun removeLast() = mPoints
        .removeLastOrNull()

    override fun onDraw(
        canvas: Canvas
    ) {
        mPoints.forEach {
            canvas.drawCircle(
                it,
                radius,
                mPaintPoint
            )
        }
    }
}