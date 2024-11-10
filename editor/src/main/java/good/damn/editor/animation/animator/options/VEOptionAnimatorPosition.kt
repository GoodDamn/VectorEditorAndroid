package good.damn.editor.animation.animator.options

import android.graphics.Canvas
import android.graphics.Paint
import good.damn.editor.animation.animator.options.tickTimer.VETickTimerAnimatorPosition
import good.damn.editor.animation.animator.options.tickTimer.base.VETickTimerAnimatorBase
import good.damn.editor.editmodes.animation.data.VEEditAnimationDataPosition
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VERectable

class VEOptionAnimatorPosition
: VEOptionAnimatorBase() {

    private val mPaintBackground = Paint().apply {
        color = 0xffb7730b.toInt()
    }

    private val mPaintText = Paint().apply {
        color = 0xffffffff.toInt()
        textSize = 15f
    }

    private var mTextX = 0f
    private var mTextY = 0f

    override val tickTimer = VETickTimerAnimatorPosition()

    override fun changeValueAnimation(
        value: Any
    ) {
        if (value !is VEEditAnimationDataPosition)
            return

        tickTimer.tickX = value.x
        tickTimer.tickY = value.y
    }

    override fun layout(
        width: Float,
        height: Float
    ) {
        super.layout(
            width,
            height
        )

        mPaintText.textSize = (width + height) * 0.2f

        mTextX = x + (width - mPaintText.textSize) * 0.51f
        mTextY = y + (height + mPaintText.textSize) * 0.45f
    }

    override fun draw(
        canvas: Canvas
    ) = canvas.run {
        drawRect(
            mRect,
            mPaintBackground
        )

        drawText(
            "XY",
            mTextX,
            mTextY,
            mPaintText
        )
    }

}