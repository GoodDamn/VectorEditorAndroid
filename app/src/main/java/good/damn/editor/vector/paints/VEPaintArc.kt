package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF
import good.damn.editor.vector.extensions.io.readFraction
import good.damn.editor.vector.extensions.io.readInt32
import good.damn.editor.vector.extensions.io.write
import good.damn.editor.vector.extensions.primitives.toByteArray
import good.damn.editor.vector.extensions.primitives.toDigitalFraction
import good.damn.editor.vector.extensions.writeToStream
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.abs
import kotlin.math.hypot

class VEPaintArc(
    canvasWidth: Float,
    canvasHeight: Float
): VEPaintBase(
    canvasWidth,
    canvasHeight
) {
    companion object {
        const val ENCODE_TYPE = 1
    }

    val rect = RectF()

    var useCenter = true

    var startAngle = 0f
    var sweepAngle = 360f

    private val mTriggerRadius = canvasWidth * 0.05f

    private val mPaintDrag = Paint().apply {
        style = Paint.Style.STROKE
        color = 0x55ffffff
        strokeWidth = mTriggerRadius * 0.5f
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        canvas.drawArc(
            rect,
            startAngle,
            sweepAngle,
            useCenter,
            mPaint
        )

        canvas.drawCircle(
            rect.left,
            rect.top,
            mTriggerRadius,
            mPaintDrag
        )

        canvas.drawCircle(
            rect.right,
            rect.bottom,
            mTriggerRadius,
            mPaintDrag
        )
    }

    override fun onDown(
        x: Float,
        y: Float
    ) {
        rect.set(
            x, y,
            x, y
        )
    }

    override fun onMove(
        moveX: Float,
        moveY: Float
    ) {
        rect.right = moveX
        rect.bottom = moveY
    }

    override fun onEncodeObject(
        os: OutputStream
    ) {
        os.apply {
            rect.writeToStream(
                this,
                mCanvasWidth,
                mCanvasHeight
            )
            write(ENCODE_TYPE)
            write(
                color.toByteArray()
            )
        }
    }

    override fun onDecodeObject(
        inp: InputStream
    ) {
        val buffer = ByteArray(4)
        rect.set(
            inp.readFraction() * mCanvasWidth,
            inp.readFraction() * mCanvasHeight,
            inp.readFraction() * mCanvasWidth,
            inp.readFraction() * mCanvasHeight
        )
        color = inp.readInt32(buffer)
    }

    override fun onCheckCollision(
        px: Float,
        py: Float
    ): Boolean {
        if (abs(hypot(
          px - rect.right,
          py - rect.bottom
        )) < mCanvasWidth * 0.05f) {

            return true
        }

        return false
    }

    override fun onUp(
        x: Float,
        y: Float
    ) {

    }
}