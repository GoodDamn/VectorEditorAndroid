package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import good.damn.editor.vector.extensions.io.readFraction
import good.damn.editor.vector.extensions.io.readInt32
import good.damn.editor.vector.extensions.io.readU
import good.damn.editor.vector.extensions.io.write
import good.damn.editor.vector.extensions.primitives.toByteArray
import good.damn.editor.vector.extensions.primitives.toDigitalFraction
import java.io.InputStream
import java.io.OutputStream
import kotlin.math.abs
import kotlin.math.hypot

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

    private var mIsDraggingPoint1 = false
    private var mIsDraggingPoint2 = false

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
        if (isAlignedHorizontal) {
            x2 = x
        }

        if (isAlignedVertical) {
            y2 = y
        }
    }

    override fun onEncodeObject(
        os: OutputStream
    )  {
        os.apply {
            write(ENCODE_TYPE)
            write(
                x1.toDigitalFraction(
                    mCanvasWidth
                )
            )
            write(
                y1.toDigitalFraction(
                    mCanvasHeight
                )
            )
            write(
                x2.toDigitalFraction(
                    mCanvasWidth
                )
            )
            write(
                y2.toDigitalFraction(
                    mCanvasHeight
                )
            )
            write(
                strokeWidth.toDigitalFraction(
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
        x1 = inp.readFraction() * mCanvasWidth
        y1 = inp.readFraction() * mCanvasHeight
        x2 = inp.readFraction() * mCanvasWidth
        y2 = inp.readFraction() * mCanvasHeight
        strokeWidth = inp.readFraction() * mCanvasWidth
        color = inp.readInt32(
            buffer
        )
    }

    override fun onDragVector(
        touchX: Float,
        touchY: Float
    ): Boolean {
        mIsDraggingPoint1 = false
        mIsDraggingPoint2 = false

        if (abs(hypot(
            x1 - touchX,
            y1 - touchY
        )) < mTriggerRadius) {
            mIsDraggingPoint1 = true
            return true
        }

        if (abs(hypot(
            x2 - touchX,
            y2 - touchY
        )) < mTriggerRadius) {
            mIsDraggingPoint2 = true
            return true
        }

        return false
    }

    override fun onDragMove(
        x: Float,
        y: Float
    ) {
        if (mIsDraggingPoint1) {
            if (isAlignedHorizontal) {
                x1 = x
            }
            if (isAlignedVertical) {
                y1 = y
            }
            return
        }

        if (mIsDraggingPoint2) {
            if (isAlignedHorizontal) {
                x2 = x
            }
            if (isAlignedVertical) {
                y2 = y
            }
        }
    }

    override fun onUp(
        x: Float,
        y: Float
    ) = Unit

}