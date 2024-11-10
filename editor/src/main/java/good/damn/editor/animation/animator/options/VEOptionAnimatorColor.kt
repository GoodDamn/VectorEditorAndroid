package good.damn.editor.animation.animator.options

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import good.damn.editor.animation.animator.options.tickTimer.VETickTimerAnimatorColor
import good.damn.editor.animation.animator.options.tickTimer.base.VETickTimerAnimatorBase
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VERectable

class VEOptionAnimatorColor
: VEOptionAnimatorBase() {

    private val mPaintGradient = Paint().apply {
        color = 0xffffffff.toInt()
    }

    override val tickTimer = VETickTimerAnimatorColor()

    override fun changeValueAnimation(
        value: Any
    ) {
        if (value !is Int)
            return

        tickTimer.color = value
    }

    override fun layout(
        width: Float,
        height: Float
    ) {
        super.layout(
            width,
            height
        )

        mPaintGradient.shader = LinearGradient(
            0f,
            0f,
            width,
            0f,
            intArrayOf(
                0xffffff00.toInt(),
                0xffff00ff.toInt()
            ),
            floatArrayOf(
                0.0f,
                1.0f
            ),
            Shader.TileMode.CLAMP
        )
    }

    override fun draw(
        canvas: Canvas
    ) = canvas.run {
        drawRect(
            mRect,
            mPaintGradient
        )
    }
}