package good.damn.editor.editmodes

import android.view.MotionEvent
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.animation.VEPointIndexedAnimation
import good.damn.editor.animation.animator.options.VEOptionAnimatorPosition
import good.damn.editor.editmodes.listeners.VEIListenerOnChangePoint
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.misc.interfaces.VEITouchable
import java.util.LinkedList

class VEEditModeAnimation(
    anchor: VEAnchor,
    skeleton: VESkeleton2D
): VEEditModeFreeMove(
    anchor,
    skeleton
) {

    var onChangePoint: VEIListenerOnChangePoint? = null

    private var mPrevHash: Int = 0

    private val mAnimatedPoints = HashMap<
        VEPointIndexed,
        VEPointIndexedAnimation
    >()

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        super.onTouchEvent(
            event
        )

        if (event.action == MotionEvent.ACTION_DOWN) {
            val foundPoint = mFoundPoint
                ?: return true

            val hash = foundPoint.hashCode()

            if (mPrevHash == hash) {
                return true
            }
            mPrevHash = hash

            var saved = mAnimatedPoints[foundPoint]

            if (saved == null) {
                saved = VEPointIndexedAnimation(
                    foundPoint,
                    arrayOf(
                        VEOptionAnimatorPosition()
                    )
                )

                mAnimatedPoints[
                    foundPoint
                ] = saved
            }

            onChangePoint?.onChangePoint(
                saved
            )
        }

        return true
    }

}