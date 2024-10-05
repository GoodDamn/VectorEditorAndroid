package good.damn.editor.vector.anchors

import android.graphics.Canvas
import android.util.Log
import kotlin.math.abs

class VEAnchorStraightHorizontal
: VEBaseAnchor() {

    companion object {
        private const val TAG = "VEAnchorStraightHorizon"
    }

    override fun onDraw(
        canvas: Canvas,
        x: Float,
        y: Float,
        x2: Float,
        y2: Float
    ) {
        onAnchorPoint?.onAnchorPoint(
            x2,
            y
        )
        
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
    ) < 30 // px
}