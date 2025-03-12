package good.damn.editor.anchors

import android.graphics.Canvas
import good.damn.sav.core.skeleton.VESkeleton2D
import kotlin.math.abs
import kotlin.math.hypot

class VEAnchorPoint(
    private val projection: VEMProjectionAnchor
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
            if (selectedIndex == it.id.id) {
                continue
            }

            if (abs(hypot(
                it.x - touchX,
                it.y - touchY
            )) < projection.radiusPointerScaled) {
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