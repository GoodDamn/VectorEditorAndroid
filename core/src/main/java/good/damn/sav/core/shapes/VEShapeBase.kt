package good.damn.sav.core.shapes

import android.graphics.Paint
import androidx.annotation.ColorInt
import good.damn.sav.core.VEIIdentifiable
import good.damn.sav.core.listeners.VEIHittable
import good.damn.sav.core.listeners.VEIPointIndexable
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.misc.interfaces.VEIDrawable

abstract class VEShapeBase
: VEIDrawable,
VEIPointIndexable,
VEIHittable,
VEIIdentifiable {

    override var index = 0

    var fill: VEIFill? = null
        set(v) {
            field = v
            v?.fillPaint(mPaint)
        }

    var strokeWidth: Float
        get() = mPaint.strokeWidth
        set(v) {
            mPaint.strokeWidth = v
        }

    protected val mPaint = Paint().apply {
        style = Paint.Style.STROKE
    }

    abstract fun shapeType(): Int

    abstract fun newInstance(
        width: Float,
        height: Float
    ): VEShapeBase
}