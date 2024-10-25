package good.damn.editor.views.animator

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import android.view.View
import good.damn.editor.views.animator.options.VEOptionAnimatorData
import good.damn.editor.views.animator.scroller.VEScrollerHorizontal
import good.damn.editor.views.animator.ticker.VEAnimatorTicker
import good.damn.sav.misc.interfaces.VEITouchable
import kotlinx.coroutines.channels.ticker

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
            VEOptionAnimatorData
    >? = null

    private val mTicker = VEAnimatorTicker()
    private val mScrollerHorizontal = VEScrollerHorizontal()

    private var mCurrentTouch: VEITouchable? = null

    var duration: Int = 500

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
            it.option.let {
                it.x = 0f
                it.y = y
                it.layout(
                    ww, hh
                )
            }

            it.tickTimer.let {
                it.x = ww
                it.y = y
                it.layoutGrid(
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
    }

    override fun onDraw(
        canvas: Canvas
    ) = canvas.run {
        super.onDraw(this)

        options?.forEach {
            it.option.draw(this)
        }

        options?.forEach {
            it.option.draw(
                canvas
            )
            it.tickTimer.scrollTimer = mScrollerHorizontal
                .scrollValue

            it.tickTimer.drawGrid(
                canvas
            )
        }

        mTicker.onDraw(
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
                if (event.x < it.option.width) {
                    it.tickTimer.tick(
                        duration,
                        mTicker.tickPosition
                    )
                }
            }

            invalidate()
        }

        return true
    }


}