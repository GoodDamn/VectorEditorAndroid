package good.damn.editor.vector.paints

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.health.connect.datatypes.HeightRecord
import androidx.annotation.ColorInt
import good.damn.editor.vector.interfaces.VEIAffectable
import good.damn.editor.vector.interfaces.VEICollidable
import good.damn.editor.vector.interfaces.VEIDecodable
import good.damn.editor.vector.interfaces.VEIDrawable
import good.damn.editor.vector.interfaces.VEIEncodable
import good.damn.editor.vector.interfaces.VEIInstasable
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
VEIAffectable,
VEIDrawable,
VEITouchable,
VEIPointable {
    override var tempPoint: PointF? = null

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

    protected val mTriggerRadius = mCanvasWidth * 0.03f

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

    protected inline fun checkRadiusCollision(
        x: Float,
        y: Float,
        cx: Float,
        cy: Float,
        radius: Float
    ) = abs(hypot(
        x - cx,
        y - cy
    )) < radius

    override fun onAffect(
        affect: VEPaintBase
    ) {
        affect.tempPoint?.let {
            tempPoint?.set(it)
        }
    }

    abstract fun newInstance(
        width: Float,
        height: Float
    ): VEPaintBase
}