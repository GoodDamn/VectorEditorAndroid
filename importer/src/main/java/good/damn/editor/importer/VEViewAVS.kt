package good.damn.editor.importer

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.annotation.RawRes

class VEViewAVS(
    context: Context
): View(
    context
) {

    private val mPaintBack = Paint()

    override fun setBackgroundColor(
        color: Int
    ) {
        mPaintBack.color = color
    }

    var model: VEModelImport? = null

    override fun onDraw(
        canvas: Canvas
    ) {
        if (mPaintBack.color != 0) {
            canvas.drawPaint(
                mPaintBack
            )
        }

        model?.shapes?.forEach {
            it.draw(canvas)
        }
    }

}