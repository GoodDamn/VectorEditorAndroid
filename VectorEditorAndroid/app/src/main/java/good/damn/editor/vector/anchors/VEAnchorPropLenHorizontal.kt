package good.damn.editor.vector.anchors

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PointF
import android.util.Log
import good.damn.editor.vector.extensions.drawLine
import good.damn.editor.vector.skeleton.VESkeleton2D
import kotlin.math.abs
import kotlin.math.hypot

class VEAnchorPropLenHorizontal(
    private val radius: Float
): VEBaseAnchor() {

    companion object {
        private const val TAG = "VEAnchorPropLenHorizont"
    }

    private var mPointFrom: PointF? = null
    private var mPointTo: PointF? = null

    init {
        mPaint.color = Color.YELLOW
    }

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

        mPointFrom = null
        mPointTo = null

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

        mPointFrom?.let { from ->
            mPointTo?.let {
                val x = it.x + abs(
                    from.x - it.x
                ) * 0.5f

                Log.d(TAG, "checkAnchor: $x;;;;; ${it.x} ${from.x}")

                if (hypot(
                    x - touchX,
                    from.y - touchY
                ) < radius) {
                    onAnchorPoint?.apply {
                        onAnchorY(
                            from.y
                        )
                        onAnchorX(x)
                    }
                    return true
                }
            }
        }

        return false
    }

}