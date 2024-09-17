package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import good.damn.editor.vector.extensions.drawCircle
import good.damn.editor.vector.extensions.io.readFraction
import good.damn.editor.vector.extensions.io.readInt32
import good.damn.editor.vector.extensions.io.write
import good.damn.editor.vector.extensions.primitives.toByteArray
import good.damn.editor.vector.extensions.primitives.toDigitalFraction
import good.damn.editor.vector.extensions.readFromStream
import good.damn.editor.vector.extensions.writeToStream
import good.damn.editor.vector.rigid.VEPointRigid
import good.damn.editor.vector.rigid.VERectRigid
import good.damn.editor.vector.rigid.readFromStream
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

    val rigidRect = RectF()

    val point1 = PointF()
    val point2 = PointF()

    var useCenter = true

    var startAngle = 0f
    var sweepAngle = 360f

    override fun onDraw(
        canvas: Canvas
    ) {

        rigidRect.left = point1.x
        rigidRect.top = point1.y
        rigidRect.right = point2.x
        rigidRect.bottom = point2.y

        canvas.drawArc(
            rigidRect,
            startAngle,
            sweepAngle,
            useCenter,
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
        point2.set(
            moveX,
            moveY
        )
    }

    override fun onEncodeObject(
        os: OutputStream
    ) {
        os.apply {
            write(ENCODE_TYPE)
            rigidRect.writeToStream(
                this,
                mCanvasWidth,
                mCanvasHeight
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
        rigidRect.readFromStream(
            inp,
            mCanvasWidth,
            mCanvasHeight
        )
        color = inp.readInt32(buffer)
    }

    override fun onCheckCollision(
        px: Float,
        py: Float
    ): Boolean {
        point1.apply {
            if (checkRadiusCollision(
                px,
                py,
                this,
                mTriggerRadius
            )) {
                tempPoint = this
                return true
            }
        }

        point2.apply {
            if (checkRadiusCollision(
                px,
                py,
                this,
                mTriggerRadius
            )) {
                tempPoint = this
                return true
            }
        }

        return false
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
    ) = VEPaintArc(
        width,
        height
    )
}