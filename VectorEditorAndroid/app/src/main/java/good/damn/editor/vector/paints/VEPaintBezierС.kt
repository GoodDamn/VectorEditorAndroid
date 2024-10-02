package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Paint
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

    override val points = Array<PointF?>(3) {null}

    init {
        mPaint.apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        mPath.apply {
            reset()

            val p0 = points[0]
                ?: return@apply

            val p1 = points[1]
                ?: return@apply

            val p2 = points[2]
                ?: return@apply

            moveTo(p0)

            cubicTo(
                p0,
                p1,
                p2
            )
        }

        canvas.drawPath(
            mPath,
            mPaint
        )

    }


    override fun onEncodeObject(
        os: OutputStream
    ) {

    }

    override fun onDecodeObject(
        inp: InputStream
    ) {

    }

    override fun newInstance(
        width: Float,
        height: Float
    ) = VEPaintBezierС(
        width,
        height
    )

}