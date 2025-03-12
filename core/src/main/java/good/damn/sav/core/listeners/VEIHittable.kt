package good.damn.sav.core.listeners

import good.damn.sav.core.VEMProjection

interface VEIHittable {
    fun checkHit(
        x: Float,
        y: Float,
        projection: VEMProjection
    ): Boolean
}