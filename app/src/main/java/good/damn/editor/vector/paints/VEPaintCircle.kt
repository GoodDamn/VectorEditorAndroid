package good.damn.editor.vector.paints

import android.graphics.Canvas
import kotlin.math.hypot

class VEPaintCircle: VEPaintBase() {

    var x = 0f
        private set
    var y = 0f
        private set
    var radius = 1f
        private set

    override fun onDraw(
        canvas: Canvas
    ) {
        canvas.drawCircle(
            x,
            y,
            radius,
            mPaint
        )
    }

    override fun onDown(
        x: Float,
        y: Float
    ) {
        this.x = x
        this.y = y
    }

    override fun onMove(
        x: Float,
        y: Float
    ) {
        radius = hypot(
            this.x - x,
            this.y - y
        )
    }

    override fun onUp(
        x: Float,
        y: Float
    ) {

    }
}