package good.damn.editor.vector.anchors

import android.graphics.Canvas
import android.graphics.PointF
import android.util.Log
import good.damn.editor.vector.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.vector.skeleton.VESkeleton2D
import java.util.LinkedList
import kotlin.math.log

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

    private val mAnchors = arrayOf(
        VEAnchorPointer(radiusPointer),
        VEAnchorStraightVertical(),
        VEAnchorStraightHorizontal()
    )
    private var mx2 = 0f
    private var my2 = 0f

    private val mAnchorsDetected = LinkedList<Anchor>()

    fun draw(
        canvas: Canvas
    ) {
        mAnchorsDetected.forEach {
            it.apply {
                anchor.onDraw(
                    canvas,
                    anchorPoint.x,
                    anchorPoint.y,
                    mx2,
                    my2
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
        mx2 = onX
        my2 = onY

        mAnchorsDetected.clear()

        onAnchorPoint?.apply {
            onAnchorX(mx2)
            onAnchorY(my2)
        }

        var i = 0
        skeleton.points.forEach { p ->
            if (p.index == selectedIndex) {
                return@forEach
            }
            mAnchors.forEach {
                if (it.checkAnchor(
                    p.x,
                    p.y,
                    mx2,
                    my2
                )) {
                    mAnchorsDetected.add(
                        Anchor(
                            p,
                            it
                        )
                    )
                }
            }
            i++
        }
    }

    private data class Anchor(
        val anchorPoint: PointF,
        val anchor: VEIAnchorable
    )
}