package good.damn.sav.core.points

import android.graphics.PointF
import good.damn.sav.core.VEIIdentifiable
import kotlin.random.Random

class VEPointIndexed(
    x: Float,
    y: Float
): PointF(
    x,y
), VEIIdentifiable {
    override var index: Int = -1
    constructor(
        point: PointF
    ): this(
        point.x,
        point.y
    )
}