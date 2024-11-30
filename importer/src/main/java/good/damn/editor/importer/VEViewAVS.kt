package good.damn.editor.importer

import android.content.Context
import android.graphics.Canvas
import android.view.View
import androidx.annotation.RawRes

class VEViewAVS(
    context: Context
): View(
    context
) {

    var model: VEModelImport? = null

    override fun onDraw(
        canvas: Canvas
    ) {
        model?.shapes?.forEach {
            it.draw(canvas)
        }
    }

}