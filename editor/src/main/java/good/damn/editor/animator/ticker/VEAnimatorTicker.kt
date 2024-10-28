package good.damn.editor.animator.ticker

import android.graphics.Canvas
import android.graphics.Paint
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

    var width = 0f
        private set

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

    private var mEndY = 0f
    private var mStartX = 0f
    private var mTickPositionX = 0f

    fun layout(
        width: Float,
        height: Float,
        x: Float
    ) {
        this.width = width
        mStartX = x
        mEndY = height
        mTickPositionX = x
    }

    override fun draw(
        canvas: Canvas
    ) {
        canvas.drawLine(
            mTickPositionX,
            0f,
            mTickPositionX + 15f,
            canvas.height.toFloat(),
            mPaint
        )
    }

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {

        if (event.y > mEndY) {
            return false
        }

        if (event.x > mStartX) {
            tickPosition = event.x - mStartX
            tickPositionFactor = tickPosition / width
            mTickPositionX = event.x
        } else {
            mTickPositionX = mStartX
        }

        return true
    }

}