package good.damn.editor.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import good.damn.editor.animation.animator.VEButtonTick
import good.damn.editor.animation.animator.options.canvas.VECanvasOption
import good.damn.editor.animation.animator.options.canvas.keyframes.VECanvasOptionKeyFramePosition
import good.damn.editor.animation.animator.options.canvas.previews.VECanvasOptionPreviewPosition
import good.damn.editor.animation.animator.scroller.VEScrollerHorizontal
import good.damn.editor.animation.animator.scroller.vertical.VEScrollerVertical
import good.damn.editor.animation.animator.ticker.VEAnimatorTicker
import good.damn.editor.animation.animators.VEAnimator
import good.damn.editor.animation.animators.VEAnimatorTick
import good.damn.sav.misc.interfaces.VEITouchable
import java.util.LinkedList
import kotlin.math.abs

class VEViewAnimatorEditor(
    context: Context?
): View(
    context
) {

    companion object {
        private val TAG = VEViewAnimatorEditor::class.simpleName
    }

    private var mOptionsDraw: Array<
        VECanvasOption
    >? = Array(1) {
        VECanvasOption(
            VECanvasOptionPreviewPosition(),
            VECanvasOptionKeyFramePosition()
        )
    }

    var duration: Int = 1000 // ms
        set(v) {
            field = v

            mTimeDividers.clear()

            durationPx = (
                v / 1000f * (width - mScrollerHorizontal.triggerEndX)
            ).toInt()

            val fDuration = duration.toFloat()
            for (i in 0..duration step 1000) {
                mTimeDividers.add(
                    TimeDivider(
                        i / fDuration * durationPx,
                        i.toString()
                    )
                )
            }

            mAnimator.duration = duration
            mBtnTick.duration = duration
        }

    var durationPx: Int = 0
        private set(v) {
            field = v
            mBtnTick.durationPx = v
        }

    val isPlaying: Boolean
        get() = mAnimator.isPlaying

    private val mAnimator = VEAnimator().apply {
        listener = VEAnimatorTick()
    }

    private val mTicker = VEAnimatorTicker()
    private val mScrollerHorizontal = VEScrollerHorizontal()
    private val mScrollerVertical = VEScrollerVertical()
    private val mTimeDividers = LinkedList<TimeDivider>()

    private val mBtnTick = VEButtonTick(
        mScrollerVertical,
        mTicker,
        mScrollerHorizontal
    )

    private var mCurrentTouch: VEITouchable? = null

    private val mPaintText = Paint().apply {
        color = mTicker.color
    }

    fun pause() {
        mAnimator.stop()
    }

    fun play(
        atTimeMs: Long = 0L
    ) {
        mAnimator.play(
            atTimeMs
        )
    }

    fun layoutEditor() {
        val heightTicker = height * 0.16f
        val widthPreview = width * 0.15f
        val widthOption = width.toFloat()

        var y = heightTicker
        mOptionsDraw?.forEach {
            it.layout(
                0f,
                y,
                widthOption,
                heightTicker
            )

            y += heightTicker
        }

        mTicker.layout(
            0f,
            heightTicker,
            widthPreview,
            widthOption
        )

        mScrollerHorizontal.apply {
            reset()
            triggerEndY = heightTicker
            triggerEndX = widthPreview
        }

        mScrollerVertical.apply {
            reset()
            triggerEndX = widthPreview * 0.5f
        }

        mPaintText.textSize = heightTicker * 0.18f

        duration = 2000
    }

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(
            changed,
            left,
            top,
            right,
            bottom
        )

        layoutEditor()
    }

    override fun onDraw(
        canvas: Canvas
    ) = canvas.run {

        super.onDraw(
            canvas
        )

        var tickX = 0f
        var tickY = mPaintText.textSize

        /*options?.firstOrNull()?.apply {
            tickX = tickTimer.x
            tickY += tickTimer.y
        }*/

        save()

        translate(
            0f,
            mScrollerVertical.scrollValue
        )

        mOptionsDraw?.forEach {
            it.draw(
                canvas
            )

            /*it.tickTimer.apply {
                scrollTimer = mScrollerHorizontal.scrollValue
                draw(canvas)
            }*/
        }

        for (it in mTimeDividers) {
            drawText(
                it.time,
                tickX + it.scrollPosition + mScrollerHorizontal.scrollValue,
                tickY,
                mPaintText
            )
        }

        val scrollDuration = ((abs(
            mScrollerHorizontal.scrollValue
        ) + mTicker.tickPosition) / durationPx * duration).toInt()

        drawText(
            scrollDuration.toString(),
            tickX + mTicker.tickPosition,
            height.toFloat(),
            mPaintText
        )

        restore()

        mTicker.draw(
            this
        )
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {
        if (event == null) {
            return false
        }

        mCurrentTouch?.let {
            if (!it.onTouchEvent(event)) {
                mCurrentTouch = null
            }
            invalidate()
            return true
        }

        if (mTicker.onTouchEvent(
            event
        )) {
            mCurrentTouch = mTicker
            invalidate()
            return true
        }

        if (mScrollerHorizontal.onTouchEvent(
            event
        )) {
            mCurrentTouch = mScrollerHorizontal
            invalidate()
            return true
        }

        if (mScrollerVertical.onTouchEvent(
            event
        )) {
            mCurrentTouch = mScrollerVertical
            invalidate()
            return true
        }

        if (mBtnTick.onTouchEvent(
            event
        )) {
            mCurrentTouch = mBtnTick
            invalidate()
            return true
        }

        return false
    }
}

private data class TimeDivider(
    val scrollPosition: Float,
    val time: String
)