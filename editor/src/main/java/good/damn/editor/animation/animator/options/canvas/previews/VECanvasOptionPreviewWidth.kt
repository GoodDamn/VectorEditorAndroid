package good.damn.editor.animation.animator.options.canvas.previews

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import good.damn.editor.transaction.VETransactionKeyFrame

class VECanvasOptionPreviewWidth(
    transactionKeyframe: VETransactionKeyFrame
): VECanvasOptionPreviewBase(
    transactionKeyframe
) {

    private val mPaintSmall = Paint().apply {
        color = 0xff00aaff.toInt()
    }

    private val mPaintMedium = Paint().apply {
        color = 0x9900aaff.toInt()
    }

    private val mRectSmall = RectF()
    private val mRectMedium = RectF()

    private var mRadius = 0f

    override fun onDraw(
        canvas: Canvas
    ) = canvas.run {
        drawRoundRect(
            mRectMedium,
            mRadius,
            mRadius,
            mPaintMedium
        )

        drawRoundRect(
            mRectSmall,
            mRadius,
            mRadius,
            mPaintSmall
        )
    }

    override fun onLayout(
        rect: RectF
    ) {
        val height = rect.height()
        mRadius = height * 0.18f

        mRectSmall.set(
            rect.left * 1.15f,
            rect.top * 1.15f,
            rect.right * 0.85f,
            rect.bottom * 0.85f
        )

        mRectMedium.set(
            rect.left * 1.1f,
            rect.top * 1.1f,
            rect.right * 0.9f,
            rect.bottom * 0.9f
        )
    }
}