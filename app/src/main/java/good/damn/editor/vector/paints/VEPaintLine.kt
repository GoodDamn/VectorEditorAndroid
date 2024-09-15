package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import good.damn.editor.vector.extensions.io.readFraction
import good.damn.editor.vector.extensions.io.readInt32
import good.damn.editor.vector.extensions.io.readU
import good.damn.editor.vector.extensions.io.write
import good.damn.editor.vector.extensions.primitives.toByteArray
import good.damn.editor.vector.extensions.primitives.toDigitalFraction
import good.damn.editor.vector.extensions.writeToStream
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

    var affectablePoint: PointF? = null
        private set

    val point1 = PointF()
    val point2 = PointF()

    private val mTriggerRadius = canvasWidth * 0.05f

    private val mPaintDrag = Paint().apply {
        style = Paint.Style.STROKE
        color = 0x55ffffff
        strokeWidth = mTriggerRadius * 0.5f
    }

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
            point1.x,
            point1.y,
            mTriggerRadius,
            mPaintDrag
        )

        canvas.drawCircle(
            point2.x,
            point2.y,
            mTriggerRadius,
            mPaintDrag
        )
    }

    override fun onCheckCollision(
        px: Float,
        py: Float
    ): Boolean {
        if (abs(hypot(
            px - point1.x,
            py - point1.y
        )) < mTriggerRadius) {
            affectablePoint = point1
            return true
        }

        if (abs(hypot(
            px - point2.x,
            py - point2.y
        )) < mTriggerRadius) {
            affectablePoint = point2
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
        affectablePoint?.apply {
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

    override fun onAffect(
        affect: VEPaintBase
    ) {
        (affect as? VEPaintLine)?.affectablePoint?.let {
            this@VEPaintLine
                .affectablePoint
                ?.set(it)
        }

        (affect as? VEPaintArc)?.rigidPoint?.let {
            this@VEPaintLine
                .affectablePoint
                ?.set(it.point)
            return
        }
        affectablePoint = null
    }

    override fun onUp(
        x: Float,
        y: Float
    ) {
        affectablePoint = null
    }

}