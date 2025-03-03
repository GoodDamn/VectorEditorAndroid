package good.damn.editor.vector.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View

class VEViewPaint(
    context: Context
): View(
    context
) {

    val paint = Paint()

    override fun onDraw(
        canvas: Canvas
    ) {
        canvas.drawPaint(
            paint
        )
    }

}