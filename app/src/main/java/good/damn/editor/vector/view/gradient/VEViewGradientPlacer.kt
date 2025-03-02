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
    context: Context
): View(
    context
) {

    private val mPaintGrad = Paint()
    private val mPaintPoint = Paint().apply {
        color = 0x99999999.toInt()
    }

    private var mCurrentPoint: PointF? = null
    private var mRadius = 0f

    private var mColors = intArrayOf()
    private var mPositions = floatArrayOf()

    private val from = PointF()
    private val to = PointF()

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
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(canvas)

        canvas.drawPaint(
            mPaintGrad
        )

        canvas.drawCircle(
            from,
            mRadius,
            mPaintPoint
        )

        canvas.drawCircle(
            to,
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
               if (mColors.size > 1) {
                mPaintGrad.shader = LinearGradient(
                    from.x,
                    from.y,
                    to.x,
                    to.y,
                    mColors,
                    mPositions,
                    Shader.TileMode.CLAMP
                )
            } }
            }

            invalidateGradient()

            onChangePosition?.onGetGradientPosition(
                from,
                to
            )

            invalidate()

            return true
        }

        if (insidePoint(
            event.x,
            event.y,
            from
        )) {
            mCurrentPoint = from
            return true
        }

        if (insidePoint(
            event.x,
            event.y,
            to
        )) {
            mCurrentPoint = to
            return true
        }

        return false
    }

    fun changeShader(
        colors: IntArray,
        positions: FloatArray
    ) {
        mColors = colors
        mPositions = positions

        invalidateGradient()
    }

    private inline fun invalidateGradient() {
        if (mColors.size > 1) {
            mPaintGrad.shader = LinearGradient(
                from.x,
                from.y,
                to.x,
                to.y,
                mColors,
                mPositions,
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