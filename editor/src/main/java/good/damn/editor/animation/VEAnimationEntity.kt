package good.damn.editor.animation

import good.damn.editor.animation.animator.options.VEOptionAnimatorBase
import good.damn.sav.core.shapes.VEShapeBase

data class VEAnimationEntity(
    val options: Array<VEOptionAnimatorBase>,
    val index: Long = System.currentTimeMillis()
)