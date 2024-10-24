package good.damn.editor.vector.views.animator.ticker

import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import good.damn.editor.vector.interfaces.VEIDrawable
import good.damn.editor.vector.interfaces.VEITouchable

class VEAnimatorTicker
: VEITouchable,
VEIDrawable {

    companion object {
        private val TAG = VEAnimatorTicker::class.simpleName
    }

    var tickPosition = 0f
        private set

    var width = 0f
        private set

    private val mPaint = Paint().apply {
        color = 0xffffaa00.toInt()
        strokeWidth = 15f
        style = Paint.Style.STROKE
    }

    private var mStartX = 0f
    private var mTickPositionX = 0f

    fun layout(
        width: Float,
        x: Float
    ) {
        this.width = width
        mStartX = x
    }

    override fun onDraw(
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
    ) = when (
        event.action
    ) {
        MotionEvent.ACTION_DOWN -> {
            event.x > mStartX
        }

        MotionEvent.ACTION_MOVE -> {
            if (event.x > mStartX) {
                tickPosition = (event.x - mStartX) / width
                mTickPositionX = mStartX + tickPosition * width
            } else {
                mTickPositionX = mStartX
            }

            true
        }
        else -> false
    }

}