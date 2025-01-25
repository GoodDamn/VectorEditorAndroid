package good.damn.editor.animation.animator.options.canvas.previews

import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import good.damn.editor.transaction.VETransactionKeyFrame

class VECanvasOptionPreviewColor(
    transactionKeyFrame: VETransactionKeyFrame
): VECanvasOptionPreviewBase(
    transactionKeyFrame
) {

    private val mPaintBack = Paint()

    override fun onDraw(
        canvas: Canvas
    ) = canvas.run {
        drawPaint(
            mPaintBack
        )
    }

    override fun onLayout(
        rect: RectF
    ) {
        mPaintBack.shader = LinearGradient(
            rect.left,
            rect.top,
            rect.right,
            rect.bottom,
            intArrayOf(
                0xffff0000.toInt(),
                0xff00ff00.toInt()
            ),
            floatArrayOf(
                0.0f,
                1.0f
            ),
            Shader.TileMode.CLAMP
        )

    }

}