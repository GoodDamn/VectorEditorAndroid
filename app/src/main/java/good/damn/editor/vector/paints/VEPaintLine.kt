package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import good.damn.editor.vector.extensions.drawCircle
import good.damn.editor.vector.extensions.io.readFraction
import good.damn.editor.vector.extensions.io.readInt32
import good.damn.editor.vector.extensions.io.readU
import good.damn.editor.vector.extensions.io.write
import good.damn.editor.vector.extensions.primitives.toByteArray
import good.damn.editor.vector.extensions.primitives.toDigitalFraction
import good.damn.editor.vector.extensions.writeToStream
import good.damn.editor.vector.interfaces.VEIPointable
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
), VEIPointable {

    companion object {
        const val ENCODE_TYPE = 0.toByte()
    }

    val point1 = PointF()
    val point2 = PointF()

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
            point1.x,
            point1.y,
            point2.x,
            point2.y,
            mPaint
        )

        canvas.drawCircle(
            point1,
            mTriggerRadius,
            mPaintDrag
        )

        canvas.drawCircle(
            point2,
            mTriggerRadius,
            mPaintDrag
        )
    }

    override fun onCheckCollision(
        px: Float,
        py: Float
    ): Boolean {
        if (checkRadiusCollision(
            px,
            py,
            point1,
            mTriggerRadius
        )) {
            tempPoint = point1
            return true
        }

        if (checkRadiusCollision(
                px,
                py,
                point2,
                mTriggerRadius
            )) {
            tempPoint = point2
            return true
        }

        return false
    }


    override fun onDown(
        x: Float,
        y: Float
    ) {
        point1.set(x,y)
        point2.set(x,y)
    }

    override fun onMove(
        moveX: Float,
        moveY: Float
    ) {
        tempPoint?.apply {
            set(moveX,moveY)
            return
        }

        point2.set(
            moveX,
            moveY
        )
    }

    override fun onEncodeObject(
        os: OutputStream
    )  {
        os.apply {
            write(ENCODE_TYPE)

            point1.writeToStream(
                this,
                mCanvasWidth,
                mCanvasHeight
            )

            point2.writeToStream(
                this,
                mCanvasWidth,
                mCanvasHeight
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

        point1.set(
            inp.readFraction() * mCanvasWidth,
            inp.readFraction() * mCanvasHeight
        )

        point2.set(
            inp.readFraction() * mCanvasWidth,
            inp.readFraction() * mCanvasHeight
        )

        strokeWidth = inp.readFraction() * mCanvasWidth
        color = inp.readInt32(
            buffer
        )
    }


    override fun onUp(
        x: Float,
        y: Float
    ) {
        tempPoint = null
    }

    override fun newInstance(
        width: Float,
        height: Float
    ) = VEPaintLine(
        width,
        height
    )
}