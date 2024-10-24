package good.damn.editor.anchors

import android.graphics.Canvas
import android.graphics.PointF
import good.damn.sav.core.skeleton.VESkeleton2D

interface VEIAnchorable {
    fun onDraw(
        canvas: Canvas
    )

    fun checkAnchor(
        skeleton: VESkeleton2D,
        touchX: Float,
        touchY: Float
    ): Boolean
}