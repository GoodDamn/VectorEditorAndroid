package good.damn.editor.vector.anchors

import android.graphics.Canvas
import android.graphics.PointF
import good.damn.editor.vector.extensions.drawLine
import good.damn.editor.vector.skeleton.VESkeleton2D
import kotlin.math.abs

class VEAnchorStraightVertical
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
        var minDownY = Float.MAX_VALUE
        var minUpY = 0f

        for (it in skeleton.points) {
            if (abs(it.x - touchX) < 30) { // px

                if (minDownY > it.y && minDownY > touchY) {
                    minDownY = it.y
                    mPointTo = it
                }

                if (minUpY < it.y && minUpY < touchY) {
                    minUpY = it.y
                    mPointFrom = it
                }
            }
        }

        mPointFrom?.let {
            onAnchorPoint?.onAnchorX(it.x)
        }

        mPointTo?.let {
            onAnchorPoint?.onAnchorX(it.x)
        }

        return !(mPointFrom == null && mPointTo == null)
    }
}