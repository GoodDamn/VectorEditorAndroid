package good.damn.sav.core.animation.keyframe

import good.damn.sav.misc.structures.tree.BinaryTree

data class VEMAnimationOptionColor(
    override val keyframes: BinaryTree<VEMKeyframeColor>,
    override var duration: Int = 0
): VEIAnimationOption<VEMKeyframeColor>