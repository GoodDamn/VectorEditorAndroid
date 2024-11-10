package good.damn.editor.editmodes.freemove

import android.view.MotionEvent
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectShape
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.interfaces.VEITouchable

open class VEEditModeExistingEntity(
    anchor: VEAnchor,
    skeleton: VESkeleton2D,
    shapes: VEListShapes
): VEITouchable {

    var onSelectShape: VEIListenerOnSelectShape?
        get() = editModeShape.onSelectShape
        set(v) {
            editModeShape.onSelectShape = v
        }

    val editModePoint = VEEditModeExistingPoint(
        skeleton,
        anchor
    )

    val editModeShape = VEEditModeExistingShape(
        shapes
    )

    private var mCurrentEditMode: VEITouchable? = null

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {

        mCurrentEditMode?.apply {
            if (!onTouchEvent(event)) {
                mCurrentEditMode = null
            }
            return true
        }

        if (editModePoint.onTouchEvent(event)) {
            mCurrentEditMode = editModePoint
            return true
        }

        if (editModeShape.onTouchEvent(event)) {
            mCurrentEditMode = editModeShape
            return true
        }

        return false
    }



}