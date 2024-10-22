package good.damn.editor.vector.views.animator.options.tickTimer

import android.graphics.Canvas
import android.graphics.Paint

class VETickTimerAnimatorColor
: VETickTimerAnimatorBase() {

    private val mPaintBack = Paint().apply {
        color = 0xffffff00.toInt()
    }

    override fun drawGrid(
        canvas: Canvas
    ) = canvas.run {
        save()

        clipRect(
            mRect
        )

        drawPaint(
            mPaintBack
        )

        restore()
    }
}