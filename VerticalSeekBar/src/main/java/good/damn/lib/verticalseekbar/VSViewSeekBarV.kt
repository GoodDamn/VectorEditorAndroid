package good.damn.lib.verticalseekbar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import good.damn.lib.verticalseekbar.interfaces.VSIListenerSeekBarProgress

class VSViewSeekBarV(
    context: Context
): View(
    context
) {
    var onSeekProgress: VSIListenerSeekBarProgress? = null

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

            minProgressY = v
            maxProgressY = layoutParams.height - v
            mDtProgress = maxProgressY - minProgressY

            mofx = v * 0.5f
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

    private var mofx = 0f

    private var minProgressY = 0f
    private var maxProgressY = 0f
    private var mDtProgress = 0f

    override fun onDraw(
        canvas: Canvas
    ) {
        canvas.drawLine(
            mofx,
            mPaintProgress.strokeWidth,
            mofx,
            maxProgressY,
            mPaintBack
        )

        canvas.drawLine(
            mofx,
            maxProgressY,
            mofx,
            minProgressY + mDtProgress * (
               1.0f - progress
            ),
            mPaintProgress
        )
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {
        if (event == null) {
            return false
        }

        progress = 1.0f - event.y / maxProgressY

        if (progress > 1.0f) {
            progress = 1.0f
        } else if (progress < 0.0f) {
            progress = 0.0f
        }

        onSeekProgress?.onSeekProgress(
            progress
        )

        return true
    }

}