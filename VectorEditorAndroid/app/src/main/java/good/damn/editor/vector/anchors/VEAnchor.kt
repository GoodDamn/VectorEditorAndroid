package good.damn.editor.vector.anchors

import android.graphics.Canvas
import android.graphics.PointF
import android.util.Log
import good.damn.editor.vector.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.vector.skeleton.VESkeleton2D
import kotlin.math.log

class VEAnchor {

    companion object {
        private const val TAG = "VEAnchor"
    }

    var onAnchorPoint: VEIListenerOnAnchorPoint? = null
        set(v) {
            field = v
            mAnchors.forEach {
                it.onAnchorPoint = v
            }
        }

    private val mAnchors = arrayOf(
        VEAnchorStraightVertical(),
        VEAnchorStraightHorizontal()
    )

    private var mx = 0f
    private var my = 0f
    private var mx2 = 0f
    private var my2 = 0f

    private var mAnchor: VEIAnchorable? = null

    fun draw(
        canvas: Canvas
    ) {
        mAnchor?.onDraw(
            canvas,
            mx,
            my,
            mx2,
            my2
        )
    }

    fun checkAnchors(
        skeleton: VESkeleton2D,
        onX: Float,
        onY: Float
    ): VEIAnchorable? {
        mx2 = onX
        my2 = onY

        mAnchor = null
        skeleton.points.forEach { p ->
            mAnchors.forEach {
                if (it.checkAnchor(
                        p.x,
                        p.y,
                        mx2,
                        my2
                    )) {
                    mAnchor = it
                    mx = p.x
                    my = p.y
                    return it
                }
            }
        }

        onAnchorPoint?.onAnchorPoint(
            mx2,
            my2
        )

        return mAnchor
    }
}