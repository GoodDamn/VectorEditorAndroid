package good.damn.editor.vector.anchors.internal

import android.graphics.Canvas
import android.graphics.PointF

interface VEIAnchorableInternal {

    fun draw(
        canvas: Canvas
    )

    fun checkAnchor(
        from: PointF,
        to: PointF,
        touch: PointF
    ): PointF?

}