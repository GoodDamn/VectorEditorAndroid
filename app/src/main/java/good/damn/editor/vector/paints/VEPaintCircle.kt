package good.damn.editor.vector.paints

import android.graphics.Canvas
import good.damn.editor.vector.extensions.primitives.toDigitalFraction
import kotlin.math.hypot

class VEPaintCircle(
    canvasWidth: Float,
    canvasHeight: Float
): VEPaintBase(
    canvasWidth,
    canvasHeight
) {
    companion object {
        val ENCODE_TYPE = 1.toByte()
    }

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

    override fun onEncodeObject() = byteArrayOf(
        ENCODE_TYPE,
        x.toDigitalFraction(mCanvasWidth),
        y.toDigitalFraction(mCanvasHeight),
        radius.toDigitalFraction(mCanvasWidth)
    )

    override fun onUp(
        x: Float,
        y: Float
    ) = Unit
}