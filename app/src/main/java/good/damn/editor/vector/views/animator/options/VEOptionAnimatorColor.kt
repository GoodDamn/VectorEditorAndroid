package good.damn.editor.vector.views.animator.options

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.RectF
import android.graphics.Shader
import good.damn.editor.vector.extensions.drawCircle

class VEOptionAnimatorColor
: VEOptionAnimatorBase() {

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
        c25y = height * 0.5f
        c25radius = (width + height) * 0.2f

        c76x = width * 0.8f
        c76y = height * 0.5f
        c76radius = (width + height) * 0.3f
    }

    override fun draw(
        canvas: Canvas
    ) {
        canvas.save()

        canvas.clipRect(
            mRect
        )

        canvas.drawCircle(
            c25x,
            c25y,
            c25radius,
            mPaintGradient25
        )

        canvas.drawCircle(
            c76x,
            c76y,
            c76radius,
            mPaintGradient76
        )

        canvas.restore()
    }
}