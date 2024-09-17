package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Point
import android.graphics.PointF
import good.damn.editor.vector.extensions.cubicTo
import good.damn.editor.vector.extensions.drawCircle
import good.damn.editor.vector.interfaces.VEIPointable
import java.io.InputStream
import java.io.OutputStream

class VEPaintBezierQ(
    canvasWidth: Float,
    canvasHeight: Float
): VEPaintBase(
    canvasWidth,
    canvasHeight
), VEIPointable {

    private val mPath = Path()

    override var tempPoint: PointF? = null

    val point1 = PointF()
    val point2 = PointF()
    val point3 = PointF()

    override fun onDraw(
        canvas: Canvas
    ) {
        mPath.reset()
        mPath.cubicTo(
            point1,
            point2,
            point3
        )

        canvas.drawPath(
            mPath,
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

        canvas.drawCircle(
            point3,
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
        point3.set(x,y)
    }

    override fun onMove(
        moveX: Float,
        moveY: Float
    ) {
        tempPoint?.apply {
            x = moveX
            y = moveY
            return
        }

        point2.x = moveX
        point2.y = moveY
    }

    override fun onUp(
        x: Float,
        y: Float
    ) {
        tempPoint = null
    }

    override fun onEncodeObject(
        os: OutputStream
    ) {

    }

    override fun onDecodeObject(
        inp: InputStream
    ) {

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

        if (checkRadiusCollision(
            px,
            py,
            point1,
            mTriggerRadius
        )) {
            tempPoint = point3
            return true
        }

        return false
    }

    override fun onAffect(
        affect: VEPaintBase
    ) {

    }


}