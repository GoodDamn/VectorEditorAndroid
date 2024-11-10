package good.damn.editor.editmodes.animation

import android.view.MotionEvent
import good.damn.editor.animation.animator.options.VEOptionAnimatorColor
import good.damn.editor.animation.tickers.VETickerColor
import good.damn.editor.editmodes.listeners.VEIListenerOnChangeValueAnimation
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectShape
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.misc.interfaces.VEITouchable

class VEEditModeAnimationShape(
    private val mShapes: VEListShapes
): VEIAnimatableEntity {

    var onSelectShape: VEIListenerOnSelectShape? = null

    private var mSelectedShape: VEShapeBase? = null

    override var onChangeValueAnimation: VEIListenerOnChangeValueAnimation? = null

    override fun getIdEntity() = (
        mSelectedShape?.index ?: -1
    ).toLong()

    override fun createAnimationEntity() = mSelectedShape?.run {
        VEEditAnimationEntity(
            arrayOf(
                VEOptionAnimatorColor().apply {
                    tickTimer.onTickColor = VETickerColor(
                        this@run
                    )
                }
            )
        )
    }

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            mSelectedShape = mShapes.find(
                event.x,
                event.y
            )?.apply {
                onSelectShape?.onSelectShape(
                    this
                )
            }

            return true
        }

        return false
    }

}