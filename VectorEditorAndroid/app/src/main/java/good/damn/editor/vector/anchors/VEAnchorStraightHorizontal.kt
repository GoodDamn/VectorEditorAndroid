package good.damn.editor.vector.anchors

import android.graphics.Canvas
import kotlin.math.abs

class VEAnchorStraightHorizontal
: VEBaseAnchor() {

    override fun onDraw(
        canvas: Canvas,
        x: Float,
        y: Float,
        x2: Float,
        y2: Float
    ) {
        canvas.drawLine(
            x,
            y,
            x2,
            y,
            mPaint
        )
    }

    override fun checkAnchor(
        x: Float,
        y: Float,
        x2: Float,
        y2: Float
    ) = abs(
        y - y2
    ) < 25
}