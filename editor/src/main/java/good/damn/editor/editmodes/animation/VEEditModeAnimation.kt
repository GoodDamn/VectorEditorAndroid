package good.damn.editor.editmodes.animation

import android.view.MotionEvent
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.editmodes.VEEditModeSwap
import good.damn.editor.editmodes.freemove.VEEditModeExistingPoint
import good.damn.editor.editmodes.freemove.VEEditModeExistingShape
import good.damn.editor.editmodes.listeners.VEIListenerOnChangeEntityAnimation
import good.damn.editor.editmodes.listeners.VEIListenerOnChangeValueAnimation
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectShape
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.skeleton.VESkeleton2D

class VEEditModeAnimation(
    anchor: VEAnchor,
    skeleton: VESkeleton2D,
    shapes: VEListShapes
): VEEditModeSwap(
    arrayOf(
        VEEditModeExistingPoint(
            skeleton,
            anchor
        ),
        VEEditModeExistingShape(
            shapes
        )
)) {

    companion object {
        private val TAG = VEEditModeAnimation::class.simpleName
    }

    var onChangeEntityAnimation: VEIListenerOnChangeEntityAnimation? = null
    var onChangeValueAnimation: VEIListenerOnChangeValueAnimation? = null

    val animatedEntities = HashMap<
        Long,
        VEEditAnimationEntity
    >()

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        super.onTouchEvent(
            event
        )

        return true
    }
}