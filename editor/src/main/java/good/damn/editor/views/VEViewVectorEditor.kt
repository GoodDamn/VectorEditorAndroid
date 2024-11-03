package good.damn.editor.views

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VEITouchable

class VEViewVectorEditor(
    context: Context,
    startMode: VEITouchable
): View(
    context
) {

    companion object {
        private const val TAG = "VEViewVector"
    }

    var mode: VEITouchable = startMode
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

        val b = mode.onTouchEvent(
            event
        )

        invalidate()

        return b
    }
}