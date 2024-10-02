package good.damn.editor.vector.options

import android.graphics.Canvas
import android.graphics.PointF
import good.damn.editor.vector.interfaces.VEIOptionable
import good.damn.editor.vector.paints.VEPaintBase
import java.util.LinkedList

class VEOptionHookPointer
: VEIOptionable {

    private var mHookedPoint: PointF? = null

    override fun runOption(
        primitives: LinkedList<VEPaintBase>,
        selectedPoint: PointF?
    ) {
        if (mHookedPoint == null) {
            mHookedPoint = selectedPoint
            return
        }
        mHookedPoint?.apply {
            selectedPoint?.let {
                set(it)
            }
        }
        mHookedPoint = null
    }

    override fun onDraw(
        canvas: Canvas
    ) = Unit
}