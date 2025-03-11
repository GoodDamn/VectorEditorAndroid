package good.damn.editor.editmodes.freemove

import android.graphics.Matrix
import android.view.MotionEvent
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.editmodes.VEEditModeTransformed
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.skeleton.VESkeleton2D

class VEEditModeExistingPoint(
    private val mSkeleton: VESkeleton2D,
    private val mAnchor: VEAnchor
): VEEditModeTransformed(),
VEIListenerOnAnchorPoint {

    var foundPoint: VEPointIndexed? = null

    override fun onTouchEvent(
        event: MotionEvent,
        invertedMatrix: Matrix
    ): Boolean {
        super.onTouchEvent(
            event,
            invertedMatrix
        )

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mAnchor.isNoDrawAnchors = false
                foundPoint = mSkeleton.find(
                    event.x,
                    event.y
                ) ?: return false

                return true
            }

            MotionEvent.ACTION_MOVE -> {
                foundPoint?.apply {
                    mAnchor.checkAnchors(
                        mSkeleton,
                        event.x,
                        event.y,
                        id.id
                    )
                }

                return true
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                mAnchor.isNoDrawAnchors = true
            }
        }

        return false
    }

    override fun onAnchorX(
        x: Float
    ) {
        foundPoint?.x = x
    }

    override fun onAnchorY(
        y: Float
    ) {
        foundPoint?.y = y
    }
}