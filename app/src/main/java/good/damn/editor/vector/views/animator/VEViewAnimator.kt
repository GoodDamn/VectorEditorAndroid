package good.damn.editor.vector.views.animator

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import good.damn.editor.vector.views.animator.options.VEOptionAnimatorData

class VEViewAnimator(
    context: Context
): View(
    context
) {

    var options: Array<
        VEOptionAnimatorData
    >? = null

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
        val ww = width * 0.25f
        val hh = height * 0.1f

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
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(canvas)
        options?.forEach {
            it.option.draw(canvas)
            it.tickTimer.drawGrid(canvas)
        }
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {

        return true
    }


}