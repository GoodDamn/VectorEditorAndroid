package good.damn.editor.vector.rigid

import android.graphics.PointF
import android.graphics.RectF
import good.damn.editor.vector.extensions.readFromStream
import java.io.InputStream

class VERectRigid {

    var top: Float
        get() = rect.top
        set(v) {
            rect.top = v
            mPoint1.y = v
        }

    var left: Float
        get() = rect.left
        set(v) {
            rect.left = v
            mPoint1.x = v
        }

    var bottom: Float
        get() = rect.bottom
        set(v) {
            rect.bottom = v
            mPoint2.y = v
        }

    var right: Float
        get() = rect.right
        set(v) {
            rect.right = v
            mPoint2.x = v
        }

    val rect = RectF()

    private val mPoint1 = PointF()
    private val mPoint2 = PointF()

    val rigidPoint1 = object : VEPointRigid(
        mPoint1,
        rect
    ) {
        override fun onRigidRect(
            point: PointF
        ) {
            targetRect.top = point.y
            targetRect.left = point.x
            this.point.set(point)
        }
    }

    val rigidPoint2 = object : VEPointRigid(
        mPoint2,
        rect
    ) {
        override fun onRigidRect(
            point: PointF
        ) {
            targetRect.bottom = point.y
            targetRect.right = point.x

            this.point.set(point)
        }
    }

    fun set(
        left: Float,
        top: Float,
        right: Float,
        bottom: Float
    ) {
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
    }

}

fun VERectRigid.readFromStream(
    inp: InputStream,
    width: Float,
    height: Float
) {
    rect.readFromStream(
        inp,
        width,
        height
    )

    top = rect.top
    left = rect.left
    bottom = rect.bottom
    right = rect.right
}