package good.damn.editor.animation.animator.ticker

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import android.view.MotionEvent
import androidx.annotation.ColorInt
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VEITouchable

class VEAnimatorTicker
: VEITouchable,
VEIDrawable {

    companion object {
        private val TAG = VEAnimatorTicker::class.simpleName
    }

    var tickPositionFactor = 0f
        private set

    var tickPosition = 0f
        private set

    val width: Float
        get() = mTriggerRect.width()

    @setparam:ColorInt
    @get:ColorInt
    var color: Int
        get() = mPaint.color
        set(v) {
            mPaint.color = v
        }

    private val mPaint = Paint().apply {
        color = 0xffffaa00.toInt()
        strokeWidth = 15f
        style = Paint.Style.STROKE
    }

    private val mTriggerRect = RectF()
    private var mTickPositionX = 0f

    fun layout(
        top2: Float,
        bottom2: Float,
        left2: Float,
        right2: Float
    ) {
        tickPosition = 0f
        tickPositionFactor = 0f
        mTriggerRect.apply {
            left = left2
            right = right2
            top = top2
            bottom = bottom2
        }

        mTickPositionX = left2
    }

    override fun draw(
        canvas: Canvas
    ) {
        mPaint.style = Paint.Style.FILL
        canvas.drawRect(
            mTriggerRect,
            mPaint
        )
        mPaint.style = Paint.Style.STROKE

        canvas.drawLine(
            mTickPositionX,
            0f,
            mTickPositionX,
            canvas.height.toFloat(),
            mPaint
        )
    }

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {

        when (
            event.action
        ) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "onTouchEvent: ${event.action} ACTION_DOWN: ${event.x} < ${mTriggerRect.left};; ${event.y} > ${mTriggerRect.bottom}")
                if ((event.y > mTriggerRect.bottom) ||
                    (event.x < mTriggerRect.left)
                ) {
                    Log.d(TAG, "onTouchEvent: ACTION_DOWN: FALSE")
                    return false
                }
            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                return false
            }
        }

        Log.d(TAG, "onTouchEvent: ${event.action} ACTION_MOVE:")

        if (event.x > mTriggerRect.left) {
            tickPosition = event.x - mTriggerRect.left + mPaint.strokeWidth * 0.5f
            tickPositionFactor = tickPosition / width
            mTickPositionX = event.x
        } else {
            mTickPositionX = mTriggerRect.left
        }

        return true
    }

}