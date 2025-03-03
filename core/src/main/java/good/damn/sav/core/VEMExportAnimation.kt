package good.damn.sav.core

data class VEMExportAnimation(
    val entity: VEIExportableAnimationEntity,
    val propertyId: Byte,
    val keyframes: List<VEIExportableKeyframe>
)