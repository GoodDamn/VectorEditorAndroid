package good.damn.editor.vector.options

import android.graphics.Canvas
import android.view.MotionEvent
import good.damn.editor.vector.VEActivityMain
import good.damn.editor.vector.anchors.VEAnchor
import good.damn.editor.vector.anchors.VEIAnchorable
import good.damn.editor.vector.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.vector.interfaces.VEIDrawable
import good.damn.editor.vector.interfaces.VEITouchable
import good.damn.editor.vector.points.VEPointIndexed
import good.damn.editor.vector.skeleton.VESkeleton2D

class VEOptionFreeMove(
    private val mAnchor: VEAnchor,
    private val mSkeleton: VESkeleton2D
): VEITouchable,
VEIListenerOnAnchorPoint {
    private var mFoundPoint: VEPointIndexed? = null

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {

        when (
            event.action
        ) {
            MotionEvent.ACTION_DOWN -> {
                mFoundPoint = mSkeleton.find(
                    event.x,
                    event.y
                )

            }

            MotionEvent.ACTION_MOVE -> {
                mFoundPoint?.apply {
                    mAnchor.checkAnchors(
                        mSkeleton,
                        event.x,
                        event.y,
                        index
                    )
                }
            }
            else ->  {}
        }

        return true
    }

    override fun onAnchorX(
        x: Float
    ) {
        mFoundPoint?.x = x
    }

    override fun onAnchorY(
        y: Float
    ) {
        mFoundPoint?.y = y
    }
}