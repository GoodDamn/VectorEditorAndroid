package good.damn.editor.animation.animator.options.canvas.keyframes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import good.damn.editor.animation.animator.scroller.VEScrollerHorizontal
import good.damn.sav.core.animation.keyframe.VEIAnimationOption
import good.damn.sav.core.animation.keyframe.VEIKeyframe

class VECanvasOptionKeyframeBase<T: VEIKeyframe>(
    private val option: VEIAnimationOption<T>
): VEICanvasOptionKeyframe {

    val scrollX: Float
        get() = mScrollerHorizontal.scrollValue

    private val mRect = RectF()

    private val mPaintBack = Paint().apply {
        color = 0xffaaaaaa.toInt()
    }

    private val mPaintKeyframe = Paint().apply {
        color = 0xffffff00.toInt()
    }

    private val mScrollerHorizontal = VEScrollerHorizontal()

    final override fun layout(
        x: Float,
        y: Float,
        width: Float,
        height: Float
    ) {
        mRect.set(
            x, y,
            x+width,
            y + height
        )

        mScrollerHorizontal.rect.set(
            mRect
        )
    }

    final override fun draw(
        canvas: Canvas
    ) = canvas.run {
        save()
        clipRect(mRect)
        drawPaint(mPaintBack)
        option.keyframes.forEach {
            drawCircle(
                mRect.left
                        + mScrollerHorizontal.scrollValue
                        + it.factor * option.duration,
                mRect.top + mRect.height() * 0.5f,
                mRect.height() * 0.25f,
                mPaintKeyframe
            )
        }
        restore()
    }

    final override fun onTouchEvent(
        event: MotionEvent
    ) = mScrollerHorizontal.onTouchEvent(
        event
    )
}