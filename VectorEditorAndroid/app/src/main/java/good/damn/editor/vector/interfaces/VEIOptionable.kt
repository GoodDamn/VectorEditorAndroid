package good.damn.editor.vector.interfaces

import android.graphics.Canvas
import android.graphics.PointF
import good.damn.editor.vector.paints.VEPaintBase
import java.util.LinkedList

interface VEIOptionable {
    fun runOption(
        primitives: LinkedList<VEPaintBase>,
        selectedPoint: PointF?
    )

    fun onDraw(
        canvas: Canvas
    )
}