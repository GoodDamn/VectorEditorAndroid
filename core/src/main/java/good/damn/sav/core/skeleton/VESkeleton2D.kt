package good.damn.sav.core.skeleton

import android.graphics.Canvas
import android.graphics.Paint
import good.damn.sav.core.VEMIdentifier
import good.damn.sav.core.listeners.VEICallbackOnAddSkeletonPoint
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.misc.extensions.drawCircle
import good.damn.sav.misc.interfaces.VEIDrawable
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
        point.id = VEMIdentifier(
            size,
            0
        )

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

    override fun draw(
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