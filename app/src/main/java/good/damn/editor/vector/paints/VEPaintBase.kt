package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import good.damn.editor.vector.interfaces.Decodable
import good.damn.editor.vector.interfaces.Encodable
import java.io.OutputStream

abstract class VEPaintBase(
    protected val mCanvasWidth: Float,
    protected val mCanvasHeight: Float
): Encodable {

    @get:ColorInt
    @setparam:ColorInt
    var color: Int
        get() = mPaint.color
        set(v) {
            mPaint.color = v
        }

    var strokeWidth: Float
        get() = mPaint.strokeWidth
        set(v) {
            mPaint.strokeWidth = v
        }

    var style: Paint.Style
        get() = mPaint.style
        set(v) {
            mPaint.style = v
        }

    protected val mPaint = Paint()
    abstract fun onDraw(
        canvas: Canvas
    )

    abstract fun onDown(
        x: Float,
        y: Float
    )
    abstract fun onMove(
        x: Float,
        y: Float
    )

    abstract fun onUp(
        x: Float,
        y: Float
    )
}