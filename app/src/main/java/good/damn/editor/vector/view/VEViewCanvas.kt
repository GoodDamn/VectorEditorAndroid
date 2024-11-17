package good.damn.editor.vector.view

import android.content.Context
import android.graphics.Canvas
import android.view.View
import good.damn.sav.misc.interfaces.VEIDrawable

class VEViewCanvas(
    context: Context
): View(
    context
) {

    var onDrawCanvas: VEIDrawable? = null

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        onDrawCanvas?.draw(
            canvas
        )
    }

}