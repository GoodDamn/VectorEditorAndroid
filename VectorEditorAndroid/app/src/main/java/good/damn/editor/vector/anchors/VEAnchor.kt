package good.damn.editor.vector.anchors

import android.graphics.Canvas
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
        VEAnchorStraightHorizontal()
    )
    private var mx2 = 0f
    private var my2 = 0f

    private val mAnchorsDetected = LinkedList<VEIAnchorable>()

    fun draw(
        canvas: Canvas
    ) {
        mAnchorsDetected.forEach {
            it.onDraw(
                canvas,
                mx2,
                my2
            )
        }
    }

    fun checkAnchors(
        skeleton: VESkeleton2D,
        onX: Float,
        onY: Float,
        selectedIndex: Int
    ) {
        mx2 = onX
        my2 = onY

        mAnchorsDetected.clear()

        onAnchorPoint?.apply {
            onAnchorX(mx2)
            onAnchorY(my2)
        }

        mAnchors.forEach {
            it.selectedIndex = selectedIndex
            if (it.checkAnchor(
                skeleton,
                mx2,
                my2
            )) {
                mAnchorsDetected.add(
                    it
                )
            }
        }
    }
}