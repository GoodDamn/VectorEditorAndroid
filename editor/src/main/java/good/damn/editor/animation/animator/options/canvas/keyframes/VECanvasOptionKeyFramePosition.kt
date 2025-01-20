package good.damn.editor.animation.animator.options.canvas.keyframes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import good.damn.sav.core.animation.VEMKeyFrame
import good.damn.sav.misc.structures.tree.BinaryTree

class VECanvasOptionKeyFramePosition
: VEICanvasOptionKeyFrame {

    private val mRect = RectF()

    private val mPaintBack = Paint().apply {
        color = 0xffaaaaaa.toInt()
    }

    private val keyFrames = BinaryTree<VEMKeyFrame>(
        equality = { v, vv ->
            v.factor == vv.factor
        },
        moreThan = { v, vv ->
            v.factor > vv.factor
        }
    )

    override fun addKeyFrame(
        keyFrame: VEMKeyFrame
    ) {
        keyFrames.add(
            keyFrame
        )
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
        restore()
    }

}