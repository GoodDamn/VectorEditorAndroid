package good.damn.sav.core

data class VEMExportAnimation(
    val entity: VEIIdentifiable,
    val propertyId: Byte,
    val keyframes: List<VEIExportable>
)