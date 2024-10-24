package good.damn.editor.anchors

import android.graphics.Canvas
import android.graphics.Point
import android.graphics.PointF
import good.damn.sav.core.skeleton.VESkeleton2D
import kotlin.math.abs
import kotlin.math.hypot

class VEAnchorPoint(
    private val radius: Float
): VEBaseAnchor() {

    override fun onDraw(
        canvas: Canvas
    ) = Unit

    override fun checkAnchor(
        skeleton: VESkeleton2D,
        touchX: Float,
        touchY: Float
    ): Boolean {
        for (it in skeleton.points) {
            if (selectedIndex == it.index) {
                continue
            }

            if (abs(hypot(
                it.x - touchX,
                it.y - touchY
            )) < radius) {
                onAnchorPoint?.apply {
                    onAnchorX(it.x)
                    onAnchorY(it.y)
                }
                return true
            }
        }

        return false
    }
}