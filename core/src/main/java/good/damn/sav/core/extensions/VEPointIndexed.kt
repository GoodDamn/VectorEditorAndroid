package good.damn.sav.core.extensions

import good.damn.sav.core.points.VEPointIndexed

inline fun VEPointIndexed.interpolateWith(
    f: Float,
    point2: VEPointIndexed?
) = if (point2 == null) {
    null
} else VEPointIndexed(
    (x + point2.x) * f,
    (y + point2.y) * f
)