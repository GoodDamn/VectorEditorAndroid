package good.damn.editor.vector.anchors

import android.graphics.Canvas
import android.graphics.PointF
import good.damn.editor.vector.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.vector.skeleton.VESkeleton2D
import java.util.LinkedList

class VEAnchor(
    radiusPointer: Float
) {

    companion object {
        private const val TAG = "VEAnchor"
    }

    var onAnchorPoint: VEIListenerOnAnchorPoint? = null
        set(v) {
            field = v
            mAnchors.forEach {
                it.onAnchorPoint = v
            }
        }

    private val mAnchors: Array<VEBaseAnchor> = arrayOf(
        VEAnchorStraightHorizontal(),
        VEAnchorStraightVertical(),
        VEAnchorPoint(radiusPointer * 0.3f)
    )

    fun draw(
        canvas: Canvas
    ) {
        mAnchors.forEach {
            if (it.isPreparedToDraw) {
                it.onDraw(
                    canvas
                )
            }
        }
    }

    fun checkAnchors(
        skeleton: VESkeleton2D,
        onX: Float,
        onY: Float,
        selectedIndex: Int
    ) {
        onAnchorPoint?.apply {
            onAnchorX(onX)
            onAnchorY(onY)
        }

        mAnchors.forEach {
            it.selectedIndex = selectedIndex
            it.isPreparedToDraw = it.checkAnchor(
                skeleton,
                onX,
                onY
            )
        }
    }
}