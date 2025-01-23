package good.damn.sav.core.animation.keyframe

import good.damn.sav.misc.structures.tree.BinaryTree

data class VEMAnimationOption(
    val keyFrames: BinaryTree<VEMKeyFrame>
)