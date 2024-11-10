package good.damn.sav.core.shapes

import android.graphics.Paint
import androidx.annotation.ColorInt
import good.damn.sav.core.VEIIndexable
import good.damn.sav.core.listeners.VEIHittable
import good.damn.sav.core.listeners.VEIPointIndexable
import good.damn.sav.misc.interfaces.VEIDrawable

abstract class VEShapeBase(
    protected val mCanvasWidth: Float,
    protected val mCanvasHeight: Float
): VEIDrawable,
VEIPointIndexable,
VEIHittable,
VEIIndexable {

    override var index = (
        System.currentTimeMillis() and 0xffff
    ).toInt()

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
    ): VEShapeBase
}