package good.damn.editor.vector.anchors

import android.graphics.Canvas
import android.graphics.PointF
import android.util.Log
import good.damn.editor.vector.extensions.drawLine
import good.damn.editor.vector.points.VEPointIndexed
import good.damn.editor.vector.skeleton.VESkeleton2D
import kotlin.math.abs

class VEAnchorStraightVertical
: VEBaseAnchor() {

    private var mPointFrom: PointF? = null
    private var mPointTo: PointF? = null

    private val mPointTouch = PointF()

    companion object {
        private const val TAG = "VEAnchorStraightVertica"
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        mPointFrom?.let { from ->
            mPointTo?.let {
                canvas.drawLine(
                    from,
                    it,
                    mPaint
                )
            }
        }
    }

    override fun checkAnchor(
        skeleton: VESkeleton2D,
        touchX: Float,
        touchY: Float
    ): Boolean {
        var minDownY = Float.MAX_VALUE
        var minUpY = 0f

        mPointTouch.set(
            touchX,
            touchY
        )

        mPointFrom = null
        mPointTo = null

        for (it in skeleton.points) {

            if (selectedIndex == it.index) {
                continue
            }

            if (abs(it.x - mPointTouch.x) < 30) { // px
                mPointTouch.x = it.x
                onAnchorPoint?.onAnchorX(
                    it.x
                )

                if (minDownY > it.y && it.y > mPointTouch.y) {
                    minDownY = it.y
                    mPointTo = it
                }

                if (minUpY < it.y && it.y < mPointTouch.y) {
                    minUpY = it.y
                    mPointFrom = it
                }
            }
        }

        if (mPointFrom == null && mPointTo == null) {
            return false
        }

        if (mPointFrom == null) {
            mPointFrom = mPointTouch
        }

        if (mPointTo == null) {
            mPointTo = mPointTouch
        }

        return true
    }

}