package good.damn.sav.core.animation.keyframe

import good.damn.sav.misc.structures.tree.BinaryTree

data class VEMAnimationOptionPosition(
    override val keyframes: BinaryTree<VEMKeyframePosition>,
    override var duration: Int = 0
): VEIAnimationOption<VEMKeyframePosition>