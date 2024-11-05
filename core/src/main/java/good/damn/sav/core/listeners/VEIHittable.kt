package good.damn.sav.core.listeners

interface VEIHittable {
    fun checkHit(
        x: Float,
        y: Float
    ): Boolean
}