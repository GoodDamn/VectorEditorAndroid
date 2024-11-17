package good.damn.sav.core.listeners

import good.damn.sav.core.points.VEPointIndexed

interface VEIPointIndexable {
    val points: MutableList<VEPointIndexed?>
}