package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Path
import android.graphics.PointF
import good.damn.editor.vector.extensions.cubicTo
import good.damn.editor.vector.extensions.drawCircle
import good.damn.editor.vector.extensions.moveTo
import java.io.InputStream
import java.io.OutputStream

class VEPaintBezierС(
    canvasWidth: Float,
    canvasHeight: Float
): VEPaintBase(
    canvasWidth,
    canvasHeight
) {

    private val mPath = Path()

    val point1 = PointF()
    val point2 = PointF()
    val point3 = PointF()

    override fun onDraw(
        canvas: Canvas
    ) {
        mPath.apply {
            reset()
            moveTo(
                point1
            )

            cubicTo(
                point1,
                point2,
                point3
            )
        }

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
        selectedPoint?.apply {
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
        selectedPoint = null
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
            selectedPoint = point1
            return true
        }

        if (checkRadiusCollision(
            px,
            py,
            point2,
            mTriggerRadius
        )) {
            selectedPoint = point2
            return true
        }

        if (checkRadiusCollision(
            px,
            py,
            point3,
            mTriggerRadius
        )) {
            selectedPoint = point3
            return true
        }

        return false
    }

    override fun newInstance(
        width: Float,
        height: Float
    ) = VEPaintBezierС(
        width,
        height
    )

}