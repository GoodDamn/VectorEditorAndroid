package good.damn.editor.vector.anchors

import android.graphics.Canvas
import android.graphics.PointF
import android.util.Log
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.extensions.drawLine
import good.damn.editor.vector.skeleton.VESkeleton2D
import kotlin.math.abs

class VEAnchorStraightHorizontal
: VEBaseAnchor() {

    companion object {
        private const val TAG = "VEAnchorStraightHorizon"
    }

    private var mPointFrom: PointF? = null
    private var mPointTo: PointF? = null

    private val mPointTouch = PointF()

    override fun onDraw(
        canvas: Canvas,
        touchX: Float,
        touchY: Float
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
        var minRightX = Float.MAX_VALUE
        var minLeftX = 0f

        mPointFrom = null
        mPointTo = null

        mPointTouch.set(
            touchX,
            touchY
        )

        for (it in skeleton.points) {
            if (abs(it.y - touchY) < 30) { // px
                mPointTouch.y = it.y
                onAnchorPoint?.onAnchorY(
                    it.y
                )

                if (minRightX > it.x && it.x > touchX) {
                    minRightX = it.x
                    mPointTo = it
                }

                if (minLeftX < it.x && it.x < touchX) {
                    minLeftX = it.x
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