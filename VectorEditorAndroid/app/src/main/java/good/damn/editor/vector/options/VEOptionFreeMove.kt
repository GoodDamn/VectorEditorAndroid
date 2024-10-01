package good.damn.editor.vector.options

import android.graphics.Canvas
import android.graphics.PointF
import good.damn.editor.vector.interfaces.VEIOptionable
import good.damn.editor.vector.paints.VEPaintBase
import java.util.LinkedList

class VEOptionFreeMove
: VEIOptionable {

    override fun runOption(
        primitives: LinkedList<VEPaintBase>,
        selectedPoint: PointF?
    ) = Unit

    override fun onDraw(
        canvas: Canvas
    ) = Unit
}