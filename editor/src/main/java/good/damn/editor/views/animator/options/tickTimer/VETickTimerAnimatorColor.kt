package good.damn.editor.views.animator.options.tickTimer

import androidx.annotation.ColorInt
import good.damn.editor.views.animator.options.tickTimer.base.VETickTimerAnimatorBase
import good.damn.editor.views.animator.options.tickTimer.data.VETickDataColor
import good.damn.sav.misc.structures.BinaryTree

class VETickTimerAnimatorColor
: VETickTimerAnimatorBase<VETickDataColor>() {

    companion object {
        private val TAG = VETickTimerAnimatorColor::class.simpleName
    }

    @setparam:ColorInt
    @get:ColorInt
    var color: Int = 0

    override fun tick(
        tickTimeMs: Int,
        tickTimeFactor: Float
    ) {
        tickList.add(
            VETickDataColor(
                tickTimeFactor,
                color
            )
        )
    }

}