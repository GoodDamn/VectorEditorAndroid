package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import good.damn.editor.vector.interfaces.VEIDecodable
import good.damn.editor.vector.interfaces.VEIDraggable
import good.damn.editor.vector.interfaces.VEIEncodable

abstract class VEPaintBase(
    protected val mCanvasWidth: Float,
    protected val mCanvasHeight: Float
): VEIEncodable,
VEIDecodable,
VEIDraggable {

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

    protected var mTriggerRadius = 0.05f * mCanvasWidth

    protected val mPaintDrag = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = mTriggerRadius * 0.25f
        color = 0xffffffff.toInt()
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