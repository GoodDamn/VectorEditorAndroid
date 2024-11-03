package good.damn.editor.editmodes

import android.util.Log
import android.view.MotionEvent
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.animation.VEPointIndexedAnimation
import good.damn.editor.animation.animator.options.VEOptionAnimatorPosition
import good.damn.editor.editmodes.listeners.VEIListenerOnChangePoint
import good.damn.editor.editmodes.listeners.VEIListenerOnChangePointPosition
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.core.points.VEPointIndexed

class VEEditModeAnimation(
    anchor: VEAnchor,
    skeleton: VESkeleton2D
): VEEditModeFreeMove(
    anchor,
    skeleton
) {

    companion object {
        private val TAG = VEEditModeAnimation::class.simpleName
    }

    var onChangePoint: VEIListenerOnChangePoint? = null
    var onChangePointPosition: VEIListenerOnChangePointPosition? = null

    private var mPrevPoint: Int = 0

    val animatedPoints = HashMap<
        Int,
        VEPointIndexedAnimation
    >()

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        super.onTouchEvent(
            event
        )

        when(
            event.action
        ) {
            MotionEvent.ACTION_DOWN -> {
                val foundPoint = mFoundPoint
                    ?: return true

                val index = foundPoint.index

                Log.d(TAG, "onTouchEvent: $index $mPrevPoint")
                if (mPrevPoint == index) {
                    return true
                }

                var saved = animatedPoints[index]

                if (saved == null) {
                    saved = VEPointIndexedAnimation(
                        foundPoint,
                        arrayOf(
                            VEOptionAnimatorPosition()
                        )
                    )

                    animatedPoints[
                        index
                    ] = saved
                }

                onChangePoint?.onChangePoint(
                    saved
                )

                mPrevPoint = index
                return true
            }


            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                mFoundPoint?.let {
                    onChangePointPosition?.onChangePointPosition(
                        it.x,
                        it.y
                    )
                }
            }

        }

        return true
    }

}