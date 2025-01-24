package good.damn.editor.animation.animator.options.canvas

import android.graphics.Canvas
import android.view.MotionEvent
import good.damn.editor.animation.animator.VEIScrollable
import good.damn.editor.animation.animator.options.canvas.keyframes.VEICanvasOptionKeyFrame
import good.damn.editor.animation.animator.options.canvas.previews.VEICanvasOptionPreview
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VEITouchable

class VECanvasOption(
    private val preview: VEICanvasOptionPreview,
    private val keyframe: VEICanvasOptionKeyFrame
): VEIDrawable,
VEITouchable {

    private var mCurrentTouch: VEITouchable? = null

    override fun draw(
        canvas: Canvas
    ) {
        keyframe.draw(canvas)
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

        keyframe.layout(
            widthPreview,
            y,
            width - widthPreview,
            height
        )
    }

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {

        mCurrentTouch?.apply {
            if (!onTouchEvent(event)) {
                mCurrentTouch = null
                return false
            }
            return true
        }

        if (preview.onTouchEvent(
            event
        )) {
            mCurrentTouch = preview
            return true
        }

        if (keyframe.onTouchEvent(
            event
        )) {
            mCurrentTouch = keyframe
            return true
        }

        return false
    }

}