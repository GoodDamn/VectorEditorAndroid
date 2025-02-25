package good.damn.sav.core.animation.keyframe

data class VEMAnimationOptionWidth(
    override val keyframes: VEKeyframes<VEMKeyframeStrokeWidth>,
    override var duration: Int
): VEIAnimationOption<VEMKeyframeStrokeWidth>