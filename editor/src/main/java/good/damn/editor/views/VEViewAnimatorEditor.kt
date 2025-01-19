package good.damn.editor.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.View
import good.damn.editor.animation.animator.VEButtonTick
import good.damn.editor.animation.animator.options.VEOptionAnimatorBase
import good.damn.editor.animation.animator.scroller.VEScrollerHorizontal
import good.damn.editor.animation.animator.scroller.vertical.VEScrollerVertical
import good.damn.editor.animation.animator.ticker.VEAnimatorTicker
import good.damn.editor.animation.animators.VEAnimator
import good.damn.editor.animation.animators.VEAnimatorTick
import good.damn.editor.animation.animators.VEIListenerAnimation
import good.damn.sav.misc.extensions.primitives.isInRange
import good.damn.sav.misc.interfaces.VEITouchable
import good.damn.sav.misc.scopes.TimeScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.LinkedList
import kotlin.math.abs

class VEViewAnimatorEditor(
    context: Context?,
    private val heightOptionFactor: Float,
    private val widthOptionFactor: Float,
    private val heightTickTriggerFactor: Float
): View(
    context
), VEIListenerAnimation {

    companion object {
        private val TAG = VEViewAnimatorEditor::class.simpleName
    }

    var tickUpdate: (()-> Unit)? = null

    var options: Array<
        VEOptionAnimatorBase
    >? = null
        set(v) {
            field = v
            mBtnTick.options = v
        }

    var duration: Int = 1000 // ms
        set(v) {
            field = v

            mTimeDividers.clear()

            durationPx = (
                v / 1000f * (width - mScrollerHorizontal.triggerEndX)
            ).toInt()

            options?.forEach {
                it.tickTimer.durationPx = durationPx
            }

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
        listener = this@VEViewAnimatorEditor
    }
    private val mAnimatorTick = VEAnimatorTick()
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

    private val mHandler = Handler(
        Looper.getMainLooper()
    )

    fun pause() {
        mAnimator.pause()
    }

    fun play(
        atTimeMs: Long = 0L
    ) {
        mAnimator.play(
            atTimeMs
        )
    }

    fun layoutEditor() {
        val tickerHeight = height * heightTickTriggerFactor
        var y = tickerHeight
        val ww = width * widthOptionFactor
        val hh = height * heightOptionFactor
        val wTimer = width - ww

        options?.forEach {
            it.let {
                it.x = 0f
                it.y = y
                it.layout(
                    ww, hh
                )
            }

            it.tickTimer.let {
                it.x = ww
                it.y = y
                it.durationPx = width
                it.layout(
                    wTimer, hh
                )
            }

            y += hh
        }

        mTicker.layout(
            0f,
            tickerHeight,
            ww,
            width.toFloat()
        )

        mScrollerHorizontal.apply {
            reset()
            triggerEndY = tickerHeight
            triggerEndX = ww
        }

        mScrollerVertical.apply {
            reset()
            triggerEndX = ww * 0.5f
        }

        mPaintText.textSize = tickerHeight * 0.18f

        duration = 5000
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

        options?.firstOrNull()?.apply {
            tickX = tickTimer.x
            tickY += tickTimer.y
        }

        save()

        translate(
            0f,
            mScrollerVertical.scrollValue
        )

        options?.forEach {
            it.draw(
                canvas
            )

            it.tickTimer.apply {
                scrollTimer = mScrollerHorizontal.scrollValue
                draw(canvas)
            }
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
            Log.d(TAG, "onTouchEvent: $mCurrentTouch")
            if (!it.onTouchEvent(event)) {
                mCurrentTouch = null
            }
            invalidate()
            return true
        }

        if (mTicker.onTouchEvent(
            event
        )) {
            Log.d(TAG, "onTouchEvent: TICKER: ${event.action}")
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

    override fun onAnimatorStart() {
        options?.apply {
            mAnimatorTick.prepare(
                this
            )
        }
    }
    override fun onAnimatorTick(
        currentTimeMs: Long,
        dt: Long
    ) {
        val durf = duration.toFloat()
        mAnimatorTick.tick(
            dt / durf
        )

        mScrollerHorizontal.scrollValue =
            currentTimeMs / durf * -durationPx

        mHandler.post {
            tickUpdate?.invoke()
            invalidate()
        }
    }

    override fun onAnimatorEnd() = Unit
}

private data class TimeDivider(
    val scrollPosition: Float,
    val time: String
)