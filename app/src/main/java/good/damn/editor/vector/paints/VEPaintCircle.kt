package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Paint
import good.damn.editor.vector.extensions.io.readFraction
import good.damn.editor.vector.extensions.io.readInt32
import good.damn.editor.vector.extensions.io.readU
import good.damn.editor.vector.extensions.io.write
import good.damn.editor.vector.extensions.primitives.toByteArray
import good.damn.editor.vector.extensions.primitives.toDigitalFraction
import java.io.InputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import kotlin.math.abs
import kotlin.math.hypot

class VEPaintCircle(
    canvasWidth: Float,
    canvasHeight: Float
): VEPaintBase(
    canvasWidth,
    canvasHeight
) {
    companion object {
        const val ENCODE_TYPE = 1
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

    override fun onEncodeObject(
        os: OutputStream
    ) {
        os.apply {
            write(ENCODE_TYPE)
            write(
                x.toDigitalFraction(
                    mCanvasWidth
                )
            )

            write(
                y.toDigitalFraction(
                    mCanvasHeight
                )
            )
            write(
                radius.toDigitalFraction(
                    mCanvasWidth
                )
            )
            write(
                color.toByteArray()
            )
        }
    }

    override fun onDecodeObject(
        inp: InputStream
    ) {
        val buffer = ByteArray(4)
        x = inp.readFraction() * mCanvasWidth
        y = inp.readFraction() * mCanvasHeight
        radius = inp.readFraction() * mCanvasWidth
        color = inp.readInt32(buffer)
    }

    override fun onCheckCollision(
        px: Float,
        py: Float
    ) = abs(hypot(
        px - x,
        py - y
    )) < mCanvasWidth * 0.05f

    override fun onUp(
        x: Float,
        y: Float
    ) = Unit
}