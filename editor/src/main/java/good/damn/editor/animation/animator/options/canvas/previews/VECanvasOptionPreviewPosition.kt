package good.damn.editor.animation.animator.options.canvas.previews

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class VECanvasOptionPreviewPosition
: VEICanvasOptionPreview {

    companion object {
        private const val TEXT_XY = "XY"
    }

    private val mPaintBack = Paint().apply {
        color = 0xff000000.toInt()
    }

    private val mPaintText = Paint().apply {
        color = 0xffffffff.toInt()
    }

    private val mRect = RectF()

    private var mTextX = 0f
    private var mTextY = 0f

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

        drawText(
            TEXT_XY,
            mTextX,
            mTextY,
            mPaintText
        )
        restore()
    }

    override fun layout(
        x: Float,
        y: Float,
        width: Float,
        height: Float
    ) {
        mRect.set(
            x, y,
            x + width,
            y + height
        )
        mPaintText.textSize = height * 0.5f
        mTextX = x + (width - mPaintText.measureText(TEXT_XY)) * 0.5f
        mTextY = y + (height + mPaintText.textSize) * 0.5f

    }
}