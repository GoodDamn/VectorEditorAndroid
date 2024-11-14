package good.damn.editor.animation.animator.options

import android.graphics.Canvas
import android.graphics.Paint
import good.damn.editor.animation.animator.options.tickTimer.VETickTimerAnimatorStrokeWidth
import good.damn.editor.animation.animator.options.tickTimer.base.VETickTimerAnimatorBase

class VEOptionAnimatorStrokeWidth
: VEOptionAnimatorBase() {

    override val tickTimer = VETickTimerAnimatorStrokeWidth()

    private val mPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = 0xffffff00.toInt()
    }

    private val mPaint2 = Paint().apply {
        style = Paint.Style.STROKE
        color = 0x99ffff00.toInt()
    }

    private var mx1 = 0f
    private var mx2 = 0f

    private var my = 0f

    override fun changeValueAnimation(
        value: Any
    ) {
        if (value !is Float)
            return

        tickTimer.strokeWidth = value
    }

    override fun layout(
        width: Float,
        height: Float
    ) {
        super.layout(
            width,
            height
        )

        val width = mRect.width()
        val height = mRect.height()

        mPaint.strokeWidth = height * 0.2f
        mPaint2.strokeWidth = height * 0.5f

        mx1 = x + width * 0.2f
        mx2 = x + width * 0.1f

        my = y + height * 0.5f
    }

    override fun draw(
        canvas: Canvas
    ) = canvas.run {
        drawLine(
            mx2,
            my,
            mRect.right - mx2,
            my,
            mPaint2
        )

        drawLine(
            mx1,
            my,
            mRect.right - mx1,
            my,
            mPaint
        )
    }

}