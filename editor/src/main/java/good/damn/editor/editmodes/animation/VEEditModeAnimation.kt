package good.damn.editor.editmodes.animation

import android.view.MotionEvent
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.editmodes.VEEditModeSwap
import good.damn.editor.editmodes.listeners.VEIListenerOnChangeEntityAnimation
import good.damn.editor.editmodes.listeners.VEIListenerOnChangeValueAnimation
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.skeleton.VESkeleton2D

class VEEditModeAnimation(
    anchor: VEAnchor,
    skeleton: VESkeleton2D,
    shapes: VEListShapes
): VEEditModeSwap<VEIAnimatableEntity>(
    arrayOf(
        VEEditModeAnimationPoint(
            skeleton,
            anchor
        ),
        VEEditModeAnimationShape(
            shapes
        )
)) {

    companion object {
        private val TAG = VEEditModeAnimation::class.simpleName
    }

    var onChangeEntityAnimation: VEIListenerOnChangeEntityAnimation? = null
    var onChangeValueAnimation: VEIListenerOnChangeValueAnimation? = null
        set(v) {
            field = v
            editModes.forEach {
                it.onChangeValueAnimation = v
            }
        }

    val animatedEntities = HashMap<
        Long,
        VEEditAnimationEntity
    >()

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        if (!super.onTouchEvent(event)) {
            return false
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val editMode = mCurrentEditMode
                    ?: return false

                val entityId = editMode.getIdEntity()

                var entity = animatedEntities[
                    entityId
                ]

                if (entity == null) {
                    entity = editMode
                        .createAnimationEntity()
                        ?: return false

                    animatedEntities[
                        entityId
                    ] = entity
                }

                onChangeEntityAnimation?.onChangeEntityAnimation(
                    entity
                )

            }

        }

        return true
    }

}