package good.damn.sav.core.animation.keyframe

data class VEMKeyframeColor(
    override val factor: Float,
    val color: ByteArray
): VEIKeyframe