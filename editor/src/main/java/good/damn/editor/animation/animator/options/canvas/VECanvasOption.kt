package good.damn.editor.animation.animator.options.canvas

import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import good.damn.editor.animation.animator.VEButtonKeyFrame
import good.damn.editor.animation.animator.VEIScrollable
import good.damn.editor.animation.animator.options.canvas.keyframes.VEICanvasOptionKeyFrame
import good.damn.editor.animation.animator.options.canvas.previews.VEICanvasOptionPreview
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VEILayoutable
import good.damn.sav.misc.interfaces.VEITouchable

class VECanvasOption(
    private val preview: VEICanvasOptionPreview,
    private val keyFrame: VEICanvasOptionKeyFrame
): VEIDrawable,
VEITouchable,
VEIScrollable {

    override var scrollX: Float
        get() = keyFrame.scrollX
        set(value) {
            keyFrame.scrollX = value
        }

    override var scrollY: Float
        get() = keyFrame.scrollY
        set(value) {
            keyFrame.scrollY = value
        }

    override fun draw(
        canvas: Canvas
    ) {
        keyFrame.draw(canvas)
        preview.draw(canvas)
    }

    fun layout(
        x: Float,
        y: Float,
        width: Float,
        widthPreview: Float,
        height: Float
    ) {
        preview.layout(
            x,
            y,
            widthPreview,
            height
        )

        keyFrame.layout(
            widthPreview,
            y,
            width - widthPreview,
            height
        )
    }

    override fun onTouchEvent(
        event: MotionEvent
    ) = preview.onTouchEvent(
        event
    )

}