package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
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

    val rigidRect = VERectRigid()

    var useCenter = true

    var startAngle = 0f
    var sweepAngle = 360f

    var rigidPoint: VEPointRigid? = null

    override fun onDraw(
        canvas: Canvas
    ) {

        canvas.drawArc(
            rigidRect.rect,
            startAngle,
            sweepAngle,
            useCenter,
            mPaint
        )

        canvas.drawCircle(
            rigidRect.rigidPoint1.point,
            mTriggerRadius,
            mPaintDrag
        )

        canvas.drawCircle(
            rigidRect.rigidPoint2.point,
            mTriggerRadius,
            mPaintDrag
        )
    }

    override fun onDown(
        x: Float,
        y: Float
    ) {
        rigidRect.set(
            x, y,
            x, y
        )
    }

    override fun onMove(
        moveX: Float,
        moveY: Float
    ) {
        rigidRect.right = moveX
        rigidRect.bottom = moveY
    }

    override fun onEncodeObject(
        os: OutputStream
    ) {
        os.apply {
            write(ENCODE_TYPE)
            rigidRect.rect.writeToStream(
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

    override fun onAffect(
        affect: VEPaintBase
    ) {
        (affect as? VEPaintLine)?.tempPoint?.apply {
            this@VEPaintArc.rigidPoint
                ?.onRigidRect(this)
            return
        }

        (affect as? VEPaintArc)?.rigidPoint?.apply {
            this@VEPaintArc.rigidPoint
                ?.onRigidRect(point)
            return
        }
    }

    override fun onCheckCollision(
        px: Float,
        py: Float
    ): Boolean {
        rigidRect.rigidPoint1.apply {
            if (checkRadiusCollision(
                px,
                py,
                point.x,
                point.y,
                mTriggerRadius
            )) {
                rigidPoint = this
                return true
            }
        }

        rigidRect.rigidPoint2.apply {
            if (checkRadiusCollision(
                    px,
                    py,
                    point.x,
                    point.y,
                    mTriggerRadius
                )) {
                rigidPoint = this
                return true
            }
        }

        return false
    }

    override fun onUp(
        x: Float,
        y: Float
    ) {
        rigidPoint = null
    }

    override fun newInstance(
        width: Float,
        height: Float
    ) = VEPaintArc(
        width,
        height
    )
}