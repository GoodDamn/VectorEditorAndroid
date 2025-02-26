package good.damn.editor.animation.animator.options.canvas.keyframes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import good.damn.editor.transaction.VEIRequesterFloat
import good.damn.editor.animation.animator.scroller.VEScrollerHorizontal
import good.damn.sav.core.animation.keyframe.VEIAnimationOption
import good.damn.sav.core.animation.keyframe.VEIKeyframe
import kotlin.math.abs
import kotlin.math.hypot

class VECanvasOptionKeyframeBase<T: VEIKeyframe>(
    private val option: VEIAnimationOption<T>,
    private val requesterTick: VEIRequesterFloat
): VEICanvasOptionKeyframe {

    val scrollX: Float
        get() = mScrollerHorizontal.scrollValue

    private val mRect = RectF()

    private val mPaintBack = Paint().apply {
        color = 0x88888888.toInt()
    }

    private val mPaintKeyframe = Paint().apply {
        color = 0xffffaa00.toInt()
    }

    private var mRadius = 0f
    private var mcy = 0f

    private var mScrollDuration = 0

    private val mScrollerHorizontal = VEScrollerHorizontal()

    private var mDurationPixels = 0f

    private var mCurrentTickPosition = 0f

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

        mRadius = mRect.height() * 0.15f

        mcy = mRect.top + mRect.height() * 0.5f

        mDurationPixels = width * option.duration / 1000f

        mPaintKeyframe.textSize = height * 0.4f

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
                mcy,
                mRadius,
                mPaintKeyframe
            )
        }

        mCurrentTickPosition = requesterTick.requestDataFloat()

        mScrollDuration = (abs(
            mScrollerHorizontal.scrollValue
        ) + mCurrentTickPosition / mDurationPixels
            * option.duration
        ).toInt()

        drawText(
            mScrollDuration.toString(),
            mCurrentTickPosition + mRect.left,
            mcy,
            mPaintKeyframe
        )

        restore()
    }

    final override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {

        var keyframe: T? = null
        option.keyframes.forEach {
            if (keyframe != null) {
                return@forEach
            }

            val x = mRect.left +
                    mScrollerHorizontal.scrollValue +
                    it.factor * option.duration

            if (hypot(
                event.x - x,
                event.y - mcy
            ) < mRadius) {
                keyframe = it
            }
        }

        keyframe?.apply {
            option.keyframes.remove(
                this
            )
            return false
        }

        return mScrollerHorizontal.onTouchEvent(
            event
        )
    }
}