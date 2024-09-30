package good.damn.editor.vector.paints

import android.graphics.Paint
import android.graphics.PointF
import androidx.annotation.ColorInt
import good.damn.editor.vector.interfaces.VEIInstansable
import good.damn.editor.vector.interfaces.VEICollidable
import good.damn.editor.vector.interfaces.VEIDecodable
import good.damn.editor.vector.interfaces.VEIDrawable
import good.damn.editor.vector.interfaces.VEIEncodable
import good.damn.editor.vector.interfaces.VEIPointable
import good.damn.editor.vector.interfaces.VEITouchable
import kotlin.math.abs
import kotlin.math.hypot

abstract class VEPaintBase(
    protected val mCanvasWidth: Float,
    protected val mCanvasHeight: Float
): VEIEncodable,
VEIDecodable,
VEICollidable,
VEIInstansable,
VEIDrawable,
VEITouchable,
VEIPointable {

    override var selectedPoint: PointF? = null

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

    protected val mTriggerRadius = mCanvasWidth * 0.05f

    protected val mPaintDrag = Paint().apply {
        style = Paint.Style.STROKE
        color = 0x55ffffff
        strokeWidth = mTriggerRadius * 0.5f
    }

    protected inline fun checkRadiusCollision(
        x: Float,
        y: Float,
        point: PointF,
        radius: Float
    ) = abs(hypot(
        x - point.x,
        y - point.y
    )) < radius

    override fun onReadInstance(
        instance: VEPaintBase
    ) {
        instance.selectedPoint?.let {
            selectedPoint?.set(it)
        }
    }

    abstract fun newInstance(
        width: Float,
        height: Float
    ): VEPaintBase
}