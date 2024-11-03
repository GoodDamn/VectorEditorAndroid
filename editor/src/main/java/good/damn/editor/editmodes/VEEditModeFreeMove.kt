package good.damn.editor.editmodes

import android.view.MotionEvent
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.misc.interfaces.VEITouchable

open class VEEditModeFreeMove(
    private val mAnchor: VEAnchor,
    private val mSkeleton: VESkeleton2D
): VEITouchable,
VEIListenerOnAnchorPoint {

    protected var mFoundPoint: VEPointIndexed? = null

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