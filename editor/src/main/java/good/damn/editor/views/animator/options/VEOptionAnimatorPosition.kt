package good.damn.editor.views.animator.options

import android.graphics.Canvas
import android.graphics.Paint
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VERectable

class VEOptionAnimatorPosition
: VERectable(),
VEIDrawable {

    private val mPaintBackground = Paint().apply {
        color = 0xffffff00.toInt()
    }

    private val mPaintText = Paint().apply {
        color = 0xffffffff.toInt()
        textSize = 15f
    }

    private var mTextX = 0f
    private var mTextY = 0f

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
        save()

        clipRect(
            mRect
        )

        drawPaint(
            mPaintBackground
        )

        drawText(
            "XY",
            mTextX,
            mTextY,
            mPaintText
        )


        restore()
    }

}