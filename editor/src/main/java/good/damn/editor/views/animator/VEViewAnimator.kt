package good.damn.editor.views.animator

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import good.damn.editor.views.animator.options.VEOptionAnimatorData
import good.damn.editor.views.animator.ticker.VEAnimatorTicker

class VEViewAnimator(
    context: Context,
    private val heightOptionFactor: Float,
    private val widthOptionFactor: Float
): View(
    context
) {

    var options: Array<
            VEOptionAnimatorData
    >? = null

    private val mTicker = VEAnimatorTicker()

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

        var y = 0f
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
            wTimer,
            ww
        )
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(canvas)

        options?.forEach {
            it.option.draw(canvas)
            it.tickTimer.drawGrid(canvas)
        }

        mTicker.onDraw(
            canvas
        )
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {
        if (event == null) {
            return false
        }

        mTicker.onTouchEvent(
            event
        )

        if (event.action == MotionEvent.ACTION_UP) {
            options?.forEach {
                if (event.x < it.option.width) {
                    it.tickTimer.tick(
                        duration,
                        mTicker.tickPosition
                    )
                }
            }
        }

        invalidate()
        return true
    }


}