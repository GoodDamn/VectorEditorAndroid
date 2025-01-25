package good.damn.editor.animation.animator.options.canvas.previews

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import good.damn.editor.transaction.VETransactionKeyFrame

class VECanvasOptionPreviewPosition(
    transactionKeyFrame: VETransactionKeyFrame
): VECanvasOptionPreviewBase(
    transactionKeyFrame
) {

    companion object {
        private const val TEXT_XY = "XY"
    }

    private val mPaintBack = Paint().apply {
        color = 0xff000000.toInt()
    }

    private val mPaintText = Paint().apply {
        color = 0xffffffff.toInt()
    }

    private var mTextX = 0f
    private var mTextY = 0f

    override fun onDraw(
        canvas: Canvas
    ) = canvas.run {
        drawPaint(
            mPaintBack
        )

        drawText(
            TEXT_XY,
            mTextX,
            mTextY,
            mPaintText
        )
    }

    override fun onLayout(
        rect: RectF
    ) {
        val height = rect.height()
        val width = rect.width()
        mPaintText.textSize = height * 0.5f
        mTextX = rect.left + (width - mPaintText.measureText(TEXT_XY)) * 0.5f
        mTextY = rect.top + (height + mPaintText.textSize) * 0.5f
    }

}