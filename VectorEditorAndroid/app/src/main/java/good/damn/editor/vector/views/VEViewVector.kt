package good.damn.editor.vector.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import good.damn.editor.vector.actions.VEDataActionPosition
import good.damn.editor.vector.actions.VEDataActionShape
import good.damn.editor.vector.actions.VEDataActionSkeletonPoint
import good.damn.editor.vector.actions.VEIActionable
import good.damn.editor.vector.options.VEIOptionable
import good.damn.editor.vector.actions.callbacks.VEICallbackOnAddShape
import good.damn.editor.vector.actions.callbacks.VEICallbackOnAddSkeletonPoint
import good.damn.editor.vector.anchors.VEAnchor
import good.damn.editor.vector.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.vector.lists.VEListShapes
import good.damn.editor.vector.shapes.VEShapeBase
import good.damn.editor.vector.points.VEPointIndexed
import good.damn.editor.vector.skeleton.VESkeleton2D
import java.util.LinkedList

class VEViewVector(
    context: Context,
    startOption: VEIOptionable
): View(
    context
) {

    companion object {
        private const val TAG = "VEViewVector"
    }

    var optionable: VEIOptionable = startOption

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        optionable.onDraw(
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