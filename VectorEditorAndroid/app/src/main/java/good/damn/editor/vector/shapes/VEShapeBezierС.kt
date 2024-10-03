package good.damn.editor.vector.shapes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import good.damn.editor.vector.extensions.cubicTo
import good.damn.editor.vector.extensions.io.readFraction
import good.damn.editor.vector.extensions.io.readInt32
import good.damn.editor.vector.extensions.io.write
import good.damn.editor.vector.extensions.moveTo
import good.damn.editor.vector.extensions.primitives.toByteArray
import good.damn.editor.vector.extensions.primitives.toDigitalFraction
import good.damn.editor.vector.extensions.writeToStreamIndexed
import good.damn.editor.vector.points.VEPointIndexed
import java.io.InputStream
import java.io.OutputStream

class VEShapeBezierС(
    canvasWidth: Float,
    canvasHeight: Float
): VEShapeBase(
    canvasWidth,
    canvasHeight
) {

    companion object {
        const val ENCODE_TYPE = 1
    }

    private val mPath = Path()

    override val points = Array<VEPointIndexed?>(3) {null}

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
    ) = os.run {
        write(
            ENCODE_TYPE
        )

        points.forEach {
            it?.writeToStreamIndexed(
                this
            )
        }

        write(
            color.toByteArray()
        )

        write(
            strokeWidth.toDigitalFraction(
                mCanvasWidth
            )
        )
    }

    override fun onDecodeObject(
        inp: InputStream
    ) = inp.run {
        val buffer = ByteArray(4)
        color = readInt32(
            buffer
        )

        strokeWidth = readFraction() * mCanvasWidth
    }

    override fun newInstance(
        width: Float,
        height: Float
    ) = VEShapeBezierС(
        width,
        height
    )

}