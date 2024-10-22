package good.damn.editor.vector.points

import android.graphics.PointF

class VEPointIndexed(
    x: Float,
    y: Float
): PointF(
    x,y
) {
    var index: Int = -1
    constructor(
        point: PointF
    ): this(
        point.x,
        point.y
    )
}