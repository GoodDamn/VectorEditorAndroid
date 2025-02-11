package good.damn.sav.core.animation.keyframe

import good.damn.sav.misc.structures.tree.BinaryTree

data class VEMAnimationOptionWidth(
    override val keyframes: VEKeyframes<VEMKeyframeWidth>,
    override var duration: Int
): VEIAnimationOption<VEMKeyframeWidth>