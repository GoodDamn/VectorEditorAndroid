package good.damn.editor.vector.skeleton

import android.graphics.PointF
import android.util.Log
import java.util.LinkedList

class VESkeleton2D {

    companion object {
        private const val TAG = "VESkeleton2D"
    }

    private val mPoints = LinkedList<
        VESkeletonPoint
    >()

    fun changePosition(
        skPoint: VESkeletonPoint,
        withX: Float,
        withY: Float
    ) {
        skPoint.points.forEach {
            it.x = withX
            it.y = withY
        }
    }

    fun find(
        withX: Float,
        withY: Float
    ): VESkeletonPoint? {
        mPoints.forEach { sk ->
            sk.points.forEach {
                Log.d(TAG, "find: ${it.x} ${it.y};;;;;; $withX $withY")
                if (it.x == withX && it.y == withY) {
                    return sk
                }
            }
        }

        return null
    }

    fun addSkeletonPoint(
        withX: Float,
        withY: Float
    ): LinkedList<PointF>? {
        var isNotFound = true

        for (skPoint in mPoints) {
            isNotFound = false
            skPoint.points.forEach { p ->
                if (!(p.x == withX && p.y == withY)) {
                    isNotFound = true
                    return@forEach
                }

            }
        }

        return if (isNotFound) {
            val list = LinkedList<PointF>()

            mPoints.add(
                VESkeletonPoint(
                    list
                )
            )

            list
        } else null
    }

    fun addSkeletonPoint(
        point: VESkeletonPoint
    ) = mPoints.add(
        point
    )
}