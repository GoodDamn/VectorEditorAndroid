package good.damn.editor.vector.views

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import good.damn.editor.vector.interfaces.VEIDrawable
import good.damn.editor.vector.interfaces.VEITouchable

class VEViewVector(
    context: Context,
    startOption: VEITouchable
): View(
    context
) {

    companion object {
        private const val TAG = "VEViewVector"
    }

    var optionable: VEITouchable = startOption
    var canvasRenderer: VEIDrawable? = null

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        canvasRenderer?.onDraw(
            canvas
        )
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {
        if (event == null) {
            return false
        }

        val b = optionable.onTouchEvent(
            event
        )

        invalidate()

        return b
    }
}