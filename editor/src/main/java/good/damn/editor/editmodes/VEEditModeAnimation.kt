package good.damn.editor.editmodes

import android.graphics.PointF
import android.view.MotionEvent
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.anchors.VEIAnchorable
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.animation.animator.options.canvas.VEIAnimationCanvas
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectPoint
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.interfaces.VEITouchable

class VEEditModeAnimation(
    private val skeleton: VESkeleton2D,
    private val anchor: VEAnchor
): VEITouchable,
VEIListenerOnAnchorPoint {

    var onSelectPoint: VEIListenerOnSelectPoint? = null

    private var mSelectedPoint: VEPointIndexed? = null

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                anchor.isNoDrawAnchors = false
                mSelectedPoint = skeleton.find(
                    event.x,
                    event.y
                ) ?: return false

                onSelectPoint?.onSelectPoint(
                    mSelectedPoint!!
                )

                return true
            }

            MotionEvent.ACTION_MOVE -> {
                mSelectedPoint?.apply {
                    anchor.checkAnchors(
                        skeleton,
                        event.x,
                        event.y,
                        index
                    )
                }

                return true
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                anchor.isNoDrawAnchors = true
            }
        }

        return true
    }

    override fun onAnchorX(
        x: Float
    ) {
        mSelectedPoint?.x = x
    }

    override fun onAnchorY(
        y: Float
    ) {
        mSelectedPoint?.y = y
    }
}