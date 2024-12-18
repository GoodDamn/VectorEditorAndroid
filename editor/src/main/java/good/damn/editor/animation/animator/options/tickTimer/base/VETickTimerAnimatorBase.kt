 package good.damn.editor.animation.animator.options.tickTimer.base

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import good.damn.editor.interfaces.VEITickable
import good.damn.editor.animation.animator.options.tickTimer.data.base.VETickData
import good.damn.editor.interfaces.VEIInterpolatable
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VEILayoutable
import good.damn.sav.misc.structures.tree.BinaryTree

abstract class VETickTimerAnimatorBase
: VEITickable,
VEIDrawable,
VEILayoutable,
VEIInterpolatable {

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

     val tickList = BinaryTree<VETickData>(
         equality = { v, v2 ->
             v == v2
         },
         moreThan = { v,v2 ->
             v.compareMoreThan(v2)
         }
     )

    override fun layout(
        width: Float,
        height: Float
    ) {
        this.width = width
        this.height = height

        mRadiusBack = height * 0.15f
        mRadius = mRadiusBack * 0.50f
    }

    override fun draw(
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
            cx = mRect.left + scrollTimer + durationPx * it.tickFactor

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

    abstract fun beginTickData(): VETickData
}