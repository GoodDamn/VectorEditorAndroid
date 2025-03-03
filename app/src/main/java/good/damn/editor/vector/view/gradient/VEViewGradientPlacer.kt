package good.damn.editor.vector.view.gradient

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Shader
import android.view.MotionEvent
import android.view.View
import good.damn.editor.vector.view.gradient.interfaces.VEIListenerOnGradientPosition
import good.damn.sav.misc.extensions.drawCircle
import kotlin.math.hypot
import kotlin.math.min

class VEViewGradientPlacer(
    private val gradientEdit: VEMGradientEdit,
    context: Context
) : View(
    context
) {

    private val mPaintGrad = Paint()
    private val mPaintPoint = Paint().apply {
        color = 0x99999999.toInt()
    }

    private var mCurrentPoint: PointF? = null
    private var mRadius = 0f

    var onChangePosition: VEIListenerOnGradientPosition? = null

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(
            changed,
            left, top,
            right, bottom
        )

        mRadius = min(
            width,
            height
        ) * 0.1f

        invalidateGradient()
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(canvas)

        canvas.drawPaint(
            mPaintGrad
        )

        canvas.drawCircle(
            gradientEdit.from,
            mRadius,
            mPaintPoint
        )

        canvas.drawCircle(
            gradientEdit.to,
            mRadius,
            mPaintPoint
        )
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {
        event ?: return false

        mCurrentPoint?.apply {

            set(
                event.x,
                event.y
            )

            when (event.action) {
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {
                    mCurrentPoint = null
                }
            }

            invalidateGradient()

            onChangePosition?.onChangeGradientPosition()

            invalidate()

            return true
        }

        if (insidePoint(
                event.x,
                event.y,
                gradientEdit.from
            )
        ) {
            mCurrentPoint = gradientEdit.from
            return true
        }

        if (insidePoint(
                event.x,
                event.y,
                gradientEdit.to
            )
        ) {
            mCurrentPoint = gradientEdit.to
            return true
        }

        return false
    }

    fun changeShader() {
        invalidateGradient()
    }

    private inline fun invalidateGradient() = gradientEdit.run {
        if (colors.size > 1) {
            mPaintGrad.shader = LinearGradient(
                from.x,
                from.y,
                to.x,
                to.y,
                colors,
                positions,
                Shader.TileMode.CLAMP
            )
        }
    }

    private inline fun insidePoint(
        x: Float,
        y: Float,
        point: PointF
    ) = hypot(
        x - point.x,
        y - point.y
    ) < mRadius
}