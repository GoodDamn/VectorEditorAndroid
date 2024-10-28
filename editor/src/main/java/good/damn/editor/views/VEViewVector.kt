package good.damn.editor.views

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VEITouchable

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

        canvasRenderer?.draw(
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