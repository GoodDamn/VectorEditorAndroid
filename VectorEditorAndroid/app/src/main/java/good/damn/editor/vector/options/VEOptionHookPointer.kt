package good.damn.editor.vector.options

import android.graphics.Canvas
import android.graphics.PointF
import good.damn.editor.vector.lists.VEListShapes
import good.damn.editor.vector.paints.VEPaintBase
import good.damn.editor.vector.skeleton.VESkeleton2D
import java.util.LinkedList

class VEOptionHookPointer
: VEIOptionable {

    private var mHookedPoint: PointF? = null

    override fun runOption(
        shapes: VEListShapes,
        selectedPoint: PointF?,
        skeleton: VESkeleton2D
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