package good.damn.sav.core.shapes

import android.graphics.Paint
import androidx.annotation.ColorInt
import good.damn.sav.core.VEIExportableAnimationEntity
import good.damn.sav.core.VEIIdentifiable
import good.damn.sav.core.VEMIdentifier
import good.damn.sav.core.animation.keyframe.VEIInterpolatablePriority
import good.damn.sav.core.listeners.VEIHittable
import good.damn.sav.core.listeners.VEIPointIndexable
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.misc.interfaces.VEIDrawable
import java.io.OutputStream

abstract class VEShapeBase
: VEIDrawable,
VEIPointIndexable,
VEIHittable,
VEIIdentifiable,
VEIExportableAnimationEntity {

    override var id = VEMIdentifier.ZERO
    override val typeEntity: Byte
        get() = 0

    override fun writeId(
        os: OutputStream
    ) = id.write(os)

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

    fun updateFillPaint() {
        fill?.fillPaint(mPaint)
    }

    abstract fun shapeType(): Int

    abstract fun newInstance(
        width: Float,
        height: Float
    ): VEShapeBase
}