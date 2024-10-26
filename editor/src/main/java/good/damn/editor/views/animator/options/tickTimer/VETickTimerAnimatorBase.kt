 package good.damn.editor.views.animator.options.tickTimer

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.Log
import good.damn.editor.views.animator.options.tickTimer.VETickTimerAnimatorColor.Companion
import good.damn.sav.misc.structures.BinaryTree
import java.util.LinkedList

open class VETickTimerAnimatorBase
: VETickTimerAnimator {

    companion object {
        private val TAG = VETickTimerAnimatorBase::class.simpleName
    }

    var x: Float
        get() = mRect.left
        set(v) {
            mRect.left = v
        }

    var y: Float
        get() = mRect.top
        set(v) {
            mRect.top = v
        }

    var width: Float
        get() = mRect.width()
        set(v) {
            mRect.right = mRect.left + v
        }

    var height: Float
        get() = mRect.height()
        set(v) {
            mRect.bottom = mRect.top + v
        }

    var scrollTimer = 0f
    var durationPx = 0

    val tickList = BinaryTree<Float>(
        equality = { v, v2 ->
            v == v2
        },
        moreThan = { v, v2 ->
            v > v2
        }
    )

    protected val mRect = RectF()

    protected val mPaintBack = Paint().apply {
        color = 0x22ffffff
    }

    protected val mPaintTick = Paint().apply {
        color = 0xffff0000.toInt()
    }

    protected val mPaintTickBack = Paint().apply {
        color = 0x99ff0000.toInt()
    }

    protected var mRadius = 0f
    protected var mRadiusBack = 0f

    override fun layoutGrid(
        width: Float,
        height: Float
    ) {
        this.width = width
        this.height = height

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

        val y = (mRect.top + mRect.bottom) * 0.5f
        var cx: Float

        tickList.forEach {
            cx = mRect.left + scrollTimer + durationPx * it

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

    override fun tick(
        tickTimeMs: Int,
        tickTimeFactor: Float
    ) {
        tickList.add(
            tickTimeFactor
        )
    }

}