package good.damn.editor.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import good.damn.editor.animator.options.VEOptionAnimatorBase
import good.damn.editor.animator.scroller.VEScrollerHorizontal
import good.damn.editor.animator.ticker.VEAnimatorTicker
import good.damn.sav.misc.extensions.primitives.isInRange
import good.damn.sav.misc.interfaces.VEITouchable
import kotlin.math.abs

class VEViewAnimator(
    context: Context,
    private val heightOptionFactor: Float,
    private val widthOptionFactor: Float
): View(
    context
) {

    companion object {
        private val TAG = VEViewAnimator::class.simpleName
    }

    var options: Array<
            VEOptionAnimatorBase
    >? = null

    private val mTicker = VEAnimatorTicker()
    private val mScrollerHorizontal = VEScrollerHorizontal()

    private var mCurrentTouch: VEITouchable? = null

    private var mPaintText = Paint().apply {
        color = mTicker.color
    }

    var duration: Int = 1000 // ms
        set(v) {
            field = v

            durationPx = (
                v / 1000f * (width - mScrollerHorizontal.triggerEndX)
            ).toInt()

            options?.forEach {
                it.tickTimer.durationPx = durationPx
            }
        }

    var durationPx: Int = 0
        private set

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

        val tickerHeight = height * 0.1f
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
            width = wTimer,
            height = tickerHeight,
            x = ww
        )

        mScrollerHorizontal.triggerEndY = tickerHeight
        mScrollerHorizontal.triggerEndX = ww

        mPaintText.textSize = height * 0.02f

        duration = 5000
    }

    override fun onDraw(
        canvas: Canvas
    ) = canvas.run {
        super.onDraw(this)

        var tickX = 0f
        var tickY = 0f
        options?.forEach {
            it.draw(
                canvas
            )

            it.tickTimer.apply {
                scrollTimer = mScrollerHorizontal.scrollValue
                draw(canvas)
                tickX = this@apply.x
                tickY = this@apply.y
            }
        }

        val scrollDuration = (abs(
            mScrollerHorizontal.scrollValue
        ) / durationPx * duration).toInt()

        val ii = (scrollDuration / 1000 + 1) * 1000
        val pos = ii.toFloat() / duration * durationPx

        drawText(
            ii.toString(),
            tickX + pos + mScrollerHorizontal.scrollValue,
            tickY,
            mPaintText
        )

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

        if (event.action == MotionEvent.ACTION_UP) {
            options?.forEach {
                if (event.x < it.width && event.y.isInRange(
                   it.y,
                   it.y+it.height
                )) {
                    val fa = (abs(
                        mScrollerHorizontal
                        .scrollValue
                    ) + mTicker.tickPosition) / durationPx

                    it.tickTimer.tick(
                        duration,
                        fa
                    )
                }
            }

            invalidate()
        }

        return true
    }


}