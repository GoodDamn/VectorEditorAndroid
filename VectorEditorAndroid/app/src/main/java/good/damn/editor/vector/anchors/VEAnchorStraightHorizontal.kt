package good.damn.editor.vector.anchors

import android.graphics.Canvas
import android.graphics.PointF
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

        for (it in skeleton.points) {
            if (abs(it.y - touchY) < 30) { // px

                if (minRightX > it.x && minRightX > touchX) {
                    minRightX = it.x
                    mPointTo = it
                }

                if (minLeftX < it.x && minLeftX < touchX) {
                    minLeftX = it.x
                    mPointFrom = it
                }
            }
        }

        mPointFrom?.let {
            onAnchorPoint?.onAnchorY(it.y)
        }

        mPointTo?.let {
            onAnchorPoint?.onAnchorY(it.y)
        }

        return !(mPointFrom == null && mPointTo == null)
    }
}