package good.damn.editor.vector.views.animator.options.tickTimer

import android.graphics.Canvas
import android.graphics.Paint
import good.damn.editor.vector.extensions.drawCircle

class VETickTimerAnimatorColor
: VETickTimerAnimatorBase() {

    private val mPaintBack = Paint().apply {
        color = 0x22ffffff
    }

    private val mPaintTick = Paint().apply {
        color = 0xffff0000.toInt()
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

        val x = mRect.width()
        val y = (mRect.top + mRect.bottom) * 0.5f
        tickList.forEach {
            drawCircle(
                mRect.left + x * it,
                y,
                25f,
                mPaintTick
            )
        }

        restore()
    }
}