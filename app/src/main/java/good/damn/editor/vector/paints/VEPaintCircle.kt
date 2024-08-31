package good.damn.editor.vector.paints

import android.graphics.Canvas
import good.damn.editor.vector.extensions.io.readFraction
import good.damn.editor.vector.extensions.io.readU
import good.damn.editor.vector.extensions.primitives.toDigitalFraction
import java.io.InputStream
import kotlin.math.hypot

class VEPaintCircle(
    canvasWidth: Float,
    canvasHeight: Float
): VEPaintBase(
    canvasWidth,
    canvasHeight
) {
    companion object {
        const val ENCODE_TYPE = 1.toByte()
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

    override fun onDecodeObject(
        inp: InputStream
    ) {
        x = inp.readFraction() * mCanvasWidth
        y = inp.readFraction() * mCanvasHeight
        radius = inp.readFraction() * mCanvasWidth
    }

    override fun onUp(
        x: Float,
        y: Float
    ) = Unit
}