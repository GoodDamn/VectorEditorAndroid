package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import good.damn.editor.vector.extensions.drawCircle
import good.damn.editor.vector.extensions.io.readFraction
import good.damn.editor.vector.extensions.io.readInt32
import good.damn.editor.vector.extensions.io.write
import good.damn.editor.vector.extensions.primitives.toByteArray
import good.damn.editor.vector.extensions.primitives.toDigitalFraction
import good.damn.editor.vector.extensions.writeToStream
import good.damn.editor.vector.extensions.writeToStreamIndexed
import good.damn.editor.vector.paints.VEPaintBezierС.Companion
import good.damn.editor.vector.points.VEPointIndexed
import java.io.InputStream
import java.io.OutputStream

class VEPaintLine(
    canvasWidth: Float,
    canvasHeight: Float
): VEPaintBase(
    canvasWidth,
    canvasHeight
) {
    companion object {
        const val ENCODE_TYPE = 0
    }

    override val points = Array<VEPointIndexed?>(2) { null }

    init {
        mPaint.apply {
            strokeCap = Paint.Cap.ROUND
            strokeJoin = Paint.Join.ROUND
        }
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        points[0]?.let { p1 ->
            points[1]?.let { p2 ->
                canvas.drawLine(
                    p1.x,
                    p1.y,
                    p2.x,
                    p2.y,
                    mPaint
                )
            }
        }
    }

    override fun onEncodeObject(
        os: OutputStream
    ) = os.run {
        write(
            VEPaintBezierС.ENCODE_TYPE
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
    ) {
        val buffer = ByteArray(4)

        points.forEach {
            it?.set(
                inp.readFraction() * mCanvasWidth,
                inp.readFraction() * mCanvasHeight
            )
        }

        strokeWidth = inp.readFraction() * mCanvasWidth
        color = inp.readInt32(
            buffer
        )
    }


    override fun newInstance(
        width: Float,
        height: Float
    ) = VEPaintLine(
        width,
        height
    )
}