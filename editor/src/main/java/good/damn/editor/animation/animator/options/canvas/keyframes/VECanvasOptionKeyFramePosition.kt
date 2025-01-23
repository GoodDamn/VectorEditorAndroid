package good.damn.editor.animation.animator.options.canvas.keyframes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import good.damn.editor.animation.animator.options.canvas.VEITransactionReceiver
import good.damn.sav.core.animation.keyframe.VEMAnimationOption
import good.damn.sav.core.animation.keyframe.VEMKeyFrame
import good.damn.sav.core.animation.keyframe.VEMKeyFrameDataPosition

class VECanvasOptionKeyFramePosition(
    private val option: VEMAnimationOption
): VEICanvasOptionKeyFrame,
VEITransactionReceiver {

    private val mRect = RectF()

    private val mPaintBack = Paint().apply {
        color = 0xffaaaaaa.toInt()
    }

    private val mPaintKeyframe = Paint().apply {
        color = 0xffffff00.toInt()
    }

    override fun layout(
        x: Float,
        y: Float,
        width: Float,
        height: Float
    ) {
        mRect.set(
            x, y,
            x+width,
            y + height
        )

    }

    override fun draw(
        canvas: Canvas
    ) = canvas.run {
        save()
        clipRect(mRect)
        drawPaint(mPaintBack)
        option.keyFrames.forEach {
            drawCircle(
                mRect.left + it.factor * mRect.width(),
                mRect.bottom * 0.75f,
                mRect.height() * 0.25f,
                mPaintKeyframe
            )
        }
        restore()
    }

    override fun onReceiveTransaction() {

        val keyFrame = VEMKeyFrame(
            0.1f,
            VEMKeyFrameDataPosition(
                50f,
                50f
            )
        )

        option.keyFrames.add(
            keyFrame
        )
    }

}