package good.damn.sav.core.animation.keyframe

data class VEMKeyframe(
    val factor: Float, // value in range 0.0 <= x <= 1.0
    val data: VEIKeyFrameData
)