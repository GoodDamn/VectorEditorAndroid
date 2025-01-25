package good.damn.sav.core.animation.keyframe

import good.damn.sav.misc.structures.tree.BinaryTree

interface VEIAnimationOption<T: VEIKeyframe> {
    val keyframes: BinaryTree<T>
    var duration: Int
}