package good.damn.editor.views.animator.options

import android.graphics.Canvas
import android.graphics.Paint
import good.damn.editor.views.animator.options.tickTimer.VETickTimerAnimatorColor
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VERectable

class VEOptionAnimatorColor
: VERectable(),
VEIDrawable {

    private val mPaintGradient25 = Paint().apply {
        color = 0x55ffffff
    }

    private val mPaintGradient76 = Paint().apply {
        color = 0x2cffffff
    }

    private var c25x = 0f
    private var c25y = 0f
    private var c25radius = 0f

    private var c76x = 0f
    private var c76y = 0f
    private var c76radius = 0f

    override fun layout(
        width: Float,
        height: Float
    ) {
        super.layout(
            width,
            height
        )

        c25x = width * 0.8f
        c25y = y + height * 0.5f
        c25radius = (width + height) * 0.2f

        c76x = width * 0.8f
        c76y = y + height * 0.5f
        c76radius = (width + height) * 0.3f
    }

    override fun draw(
        canvas: Canvas
    ) = canvas.run {
        save()

        clipRect(
            mRect
        )

        drawCircle(
            c25x,
            c25y,
            c25radius,
            mPaintGradient25
        )

        drawCircle(
            c76x,
            c76y,
            c76radius,
            mPaintGradient76
        )
        restore()
    }
}