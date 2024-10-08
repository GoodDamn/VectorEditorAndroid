package good.damn.editor.vector.anchors

import android.graphics.Canvas
import android.graphics.Paint
import kotlin.math.abs
import kotlin.math.hypot

class VEAnchorPointer(
    private val mRadius: Float
): VEBaseAnchor() {

    init {
        mPaint.style = Paint.Style.FILL
    }

    override fun checkAnchor(
        x: Float,
        y: Float,
        x2: Float,
        y2: Float
    ): Boolean {
        val b = hypot(
            x - x2,
            y - y2
        ) < mRadius

        if (b) {
            onAnchorPoint?.apply {
                onAnchorX(x)
                onAnchorY(y)
            }
        }

        return b
    }

    override fun onDraw(
        canvas: Canvas,
        x: Float,
        y: Float,
        x2: Float,
        y2: Float
    ) {
        canvas.drawCircle(
            x,
            y,
            mRadius,
            mPaint
        )
    }
}