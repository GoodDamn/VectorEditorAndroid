package good.damn.editor.views.animator.options.tickTimer

import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log

class VETickTimerAnimatorColor
: VETickTimerAnimatorBase() {

    companion object {
        private val TAG = VETickTimerAnimatorColor::class.simpleName
    }

    private val mPaintBack = Paint().apply {
        color = 0x22ffffff
    }

    private val mPaintTick = Paint().apply {
        color = 0xffff0000.toInt()
    }

    private val mPaintTickBack = Paint().apply {
        color = 0x99ff0000.toInt()
    }

    private var mRadius = 0f
    private var mRadiusBack = 0f

    override fun layoutGrid(
        width: Float,
        height: Float
    ) {
        super.layoutGrid(
            width,
            height
        )

        mRadiusBack = height * 0.15f
        mRadius = mRadiusBack * 0.50f
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

        val x = mRect.width()it
        val y = (mRect.top + mRect.bottom) * 0.5f
        var cx: Float
        tickList.forEach {
            cx = mRect.left + scrollTimer + x * it

            drawCircle(
                cx,
                y,
                mRadiusBack,
                mPaintTickBack
            )

            drawCircle(
                cx,
                y,
                mRadius,
                mPaintTick
            )
        }

        restore()
    }
}