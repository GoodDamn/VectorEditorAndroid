package good.damn.editor.anchors

import android.graphics.Canvas
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.sav.core.VEMProjection

class VEAnchor(
    projection: VEMProjectionAnchor
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
        VEAnchorStraightHorizontal(projection),
        VEAnchorStraightVertical(projection),
        VEAnchorPoint(projection)
    )

    var isNoDrawAnchors = false

    fun draw(
        canvas: Canvas
    ) {
        if (isNoDrawAnchors) {
            return
        }

        mAnchors.forEach {
            if (it.isPreparedToDraw) {
                it.onDraw(
                    canvas
                )
            }
        }
    }

    fun checkAnchors(
        skeleton: good.damn.sav.core.skeleton.VESkeleton2D,
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