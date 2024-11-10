package good.damn.editor.editmodes

import android.view.MotionEvent
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.animation.VEAnimationEntity
import good.damn.editor.animation.VETickerPosition
import good.damn.editor.animation.animator.options.VEOptionAnimatorPosition
import good.damn.editor.editmodes.freemove.VEEditModeExistingEntity
import good.damn.editor.editmodes.listeners.VEIListenerOnChangeEntityAnimation
import good.damn.editor.editmodes.listeners.VEIListenerOnChangeValueAnimation
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.skeleton.VESkeleton2D

class VEEditModeAnimation(
    anchor: VEAnchor,
    skeleton: VESkeleton2D,
    shapes: VEListShapes
): VEEditModeExistingEntity(
    anchor,
    skeleton,
    shapes
) {

    companion object {
        private val TAG = VEEditModeAnimation::class.simpleName
    }

    var onChangeEntityAnimation: VEIListenerOnChangeEntityAnimation? = null
    var onChangeValueAnimation: VEIListenerOnChangeValueAnimation? = null

    val animatedEntities = HashMap<
        Long,
        VEAnimationEntity
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