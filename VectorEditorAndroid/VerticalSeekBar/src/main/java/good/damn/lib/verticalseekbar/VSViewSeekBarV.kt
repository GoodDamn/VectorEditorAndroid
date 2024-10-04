package good.damn.lib.verticalseekbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt

class VSViewSeekBarV(
    context: Context
): View(
    context
) {

    @setparam:ColorInt
    @get:ColorInt
    var progressColor: Int
        get() = mPaintProgress.color
        set(v) {
            mPaintProgress.color = v
        }

    override fun setBackgroundColor(
        color: Int
    ) {
        mPaintBack.color = color
    }

    fun getBackgroundColor() = mPaintBack.color

    var strokeWidth: Float
        get() = mPaintProgress.strokeWidth
        set(v) {
            mPaintBack.strokeWidth = v
            mPaintProgress.strokeWidth = v
        }

    var progress = 0.5f
        set(v) {
            field = v
            invalidate()
        }

    private val mPaintProgress = Paint().apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val mPaintBack = Paint().apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        val x = mPaintProgress.strokeWidth * 0.5f
        val ofx = height - mPaintProgress.strokeWidth
        canvas.drawLine(
            x,
            mPaintProgress.strokeWidth,
            x,
            ofx,
            mPaintBack
        )

        canvas.drawLine(
            x,
            ofx,
            x,
            height * progress,
            mPaintProgress
        )
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {
        if (event == null) {
            return false
        }

        progress = event.y / height
        return true
    }

}