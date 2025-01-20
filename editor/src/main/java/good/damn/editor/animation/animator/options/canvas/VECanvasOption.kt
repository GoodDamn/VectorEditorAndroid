package good.damn.editor.animation.animator.options.canvas

import android.graphics.Canvas
import good.damn.editor.animation.animator.options.canvas.keyframes.VEICanvasOptionKeyFrame
import good.damn.editor.animation.animator.options.canvas.previews.VEICanvasOptionPreview
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VEILayoutable

class VECanvasOption(
    private val preview: VEICanvasOptionPreview,
    private val keyFrame: VEICanvasOptionKeyFrame
): VEILayoutable,
VEIDrawable {

    override fun draw(
        canvas: Canvas
    ) {
        preview.draw(canvas)
        keyFrame.draw(canvas)
    }

    override fun layout(
        x: Float,
        y: Float,
        width: Float,
        height: Float
    ) {
        val xKeyFrame = width * 0.15f
        preview.layout(
            0f,
            y,
            xKeyFrame,
            height
        )

        keyFrame.layout(
            xKeyFrame,
            y,
            width - xKeyFrame,
            height
        )
    }
}