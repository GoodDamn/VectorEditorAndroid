package good.damn.editor.editmodes.animation

import android.view.MotionEvent
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.animation.tickers.VETickerPosition
import good.damn.editor.animation.animator.options.VEOptionAnimatorPosition
import good.damn.editor.editmodes.animation.data.VEEditAnimationDataPosition
import good.damn.editor.editmodes.listeners.VEIListenerOnChangeValueAnimation
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.skeleton.VESkeleton2D

class VEEditModeAnimationPoint(
    private val mSkeleton: VESkeleton2D,
    private val mAnchor: VEAnchor
): VEIAnimatableEntity,
VEIListenerOnAnchorPoint {

    private var mFoundPoint: VEPointIndexed? = null

    override var onChangeValueAnimation: VEIListenerOnChangeValueAnimation? = null

    override fun getIdEntity() = (
        mFoundPoint?.index ?: 0
    ).toLong()

    override fun createAnimationEntity() = mFoundPoint?.run {
        VEEditAnimationEntity(
            arrayOf(
                VEOptionAnimatorPosition().apply {
                    tickTimer.onTickPosition = VETickerPosition(
                        this@run
                    )
                }
            )
        )
    }

    private var mDataPosition: VEEditAnimationDataPosition? = null

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mAnchor.isNoDrawAnchors = false
                mFoundPoint = mSkeleton.find(
                    event.x,
                    event.y
                ) ?: return false

                mDataPosition = VEEditAnimationDataPosition().apply {
                    x = event.x
                    y = event.y
                }
                return true
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

                return true
            }


            MotionEvent.ACTION_UP -> {
                mAnchor.isNoDrawAnchors = true
                mFoundPoint?.let {
                    mDataPosition?.apply {
                        x = it.x
                        y = it.y
                        onChangeValueAnimation?.onChangeValueAnimation(
                            this
                        )
                    }
                }
                mDataPosition = null
                return true
            }
        }

        return false
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