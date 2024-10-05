package good.damn.editor.vector.anchors

import android.graphics.Canvas
import android.graphics.PointF
import good.damn.editor.vector.skeleton.VESkeleton2D

class VEAnchor {

    private val mAnchors = arrayOf(
        VEAnchorStraightVertical(),
        VEAnchorStraightHorizontal()
    )

    fun checkAnchors(
        canvas: Canvas,
        skeleton: VESkeleton2D,
        c: PointF
    ) {
        skeleton.forEach { p ->
            mAnchors.forEach {
                if (it.checkAnchor(
                    p.x,
                    p.y,
                    c.x,
                    c.y
                )) {
                    it.onDraw(
                        canvas,
                        p.x,
                        p.y,
                        c.x,
                        c.y
                    )
                }
            }
        }
    }
}