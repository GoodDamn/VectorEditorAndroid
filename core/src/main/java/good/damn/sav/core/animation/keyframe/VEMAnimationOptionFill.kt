package good.damn.sav.core.animation.keyframe

import good.damn.sav.core.animation.keyframe.fill.VEMKeyframeFill

data class VEMAnimationOptionFill(
    override val keyframes: VEKeyframes<VEMKeyframeFill>,
    override var duration: Int = 0
): VEIAnimationOption<VEMKeyframeFill>