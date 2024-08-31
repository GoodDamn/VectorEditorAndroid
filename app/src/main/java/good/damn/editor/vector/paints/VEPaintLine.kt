package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import good.damn.editor.vector.extensions.primitives.toDigitalFraction

class VEPaintLine(
    canvasWidth: Float,
    canvasHeight: Float
): VEPaintBase(
    canvasWidth,
    canvasHeight
) {

    companion object {
        const val ENCODE_TYPE = 0.toByte()
    }

    var x1 = 0f
        private set
    var y1 = 0f
        private set
    var x2 = 0f
        private set
    var y2 = 0f
        private set

    init {
        mPaint.apply {
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        canvas.drawLine(
            x1,
            y1,
            x2,
            y2,
            mPaint
        )
    }

    override fun onDown(
        x: Float,
        y: Float
    ) {
        x1 = x
        x2 = x

        y1 = y
        y2 = y
    }

    override fun onMove(
        x: Float,
        y: Float
    ) {
        x2 = x
        y2 = y
    }

    override fun onEncodeObject() = byteArrayOf(
        ENCODE_TYPE,
        x1.toDigitalFraction(mCanvasWidth),
        y1.toDigitalFraction(mCanvasHeight),
        x2.toDigitalFraction(mCanvasWidth),
        y2.toDigitalFraction(mCanvasHeight),
        strokeWidth.toDigitalFraction(mCanvasWidth)
    )

    override fun onUp(
        x: Float,
        y: Float
    ) = Unit

}