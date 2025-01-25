package good.damn.editor.animation.animator.options.canvas.previews

import android.graphics.Canvas
import android.graphics.RectF
import android.view.MotionEvent
import android.view.View
import good.damn.editor.animation.animator.VEButtonKeyFrame
import good.damn.editor.transaction.VEITransactionRequest

abstract class VECanvasOptionPreviewBase(
    private val transactionKeyFrame: VEITransactionRequest
): VEICanvasOptionPreview,
View.OnClickListener {

    private val mButtonKeyFrame = VEButtonKeyFrame().apply {
        onClickListener = this@VECanvasOptionPreviewBase
    }

    private val mRect = RectF()

    final override fun draw(
        canvas: Canvas
    ) = canvas.run {
        save()
        clipRect(mRect)
        onDraw(canvas)
        restore()
    }

    final override fun layout(
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

        onLayout(
            mRect
        )

        val btnKeyFrameWidth = width * 0.3f
        mButtonKeyFrame.layout(
            width - btnKeyFrameWidth,
            y,
            btnKeyFrameWidth,
            height
        )
    }

    final override fun onTouchEvent(
        event: MotionEvent
    ) = mButtonKeyFrame.onTouchEvent(
        event
    )

    final override fun onClick(
        v: View?
    ) {
        transactionKeyFrame.requestTransaction()
    }

    protected abstract fun onDraw(
        canvas: Canvas
    )

    protected abstract fun onLayout(
        rect: RectF
    )

}