package good.damn.sav.core.animation.keyframe

import good.damn.sav.misc.structures.tree.BinaryTree

interface VEIAnimationOption {
    val keyframes: BinaryTree<VEMKeyframe>
    var duration: Int
}