package good.damn.editor.vector.paints

import android.graphics.Paint
import android.graphics.PointF
import androidx.annotation.ColorInt
import good.damn.editor.vector.interfaces.VEIAnchorable
import good.damn.editor.vector.interfaces.VEIInstansable
import good.damn.editor.vector.interfaces.VEIDecodable
import good.damn.editor.vector.interfaces.VEIDrawable
import good.damn.editor.vector.interfaces.VEIEncodable
import kotlin.math.abs
import kotlin.math.hypot

abstract class VEPaintBase(
    protected val mCanvasWidth: Float,
    protected val mCanvasHeight: Float
): VEIEncodable,
VEIDecodable,
VEIDrawable,
VEIAnchorable {

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

    abstract fun newInstance(
        width: Float,
        height: Float
    ): VEPaintBase
}