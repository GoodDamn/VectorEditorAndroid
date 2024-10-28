package good.damn.editor.animator.options.tickTimer

import android.graphics.Canvas
import good.damn.editor.animator.options.tickTimer.base.VETickTimerAnimatorBase
import good.damn.editor.animator.options.tickTimer.data.VETickDataPosition
import good.damn.sav.misc.structures.BinaryTree

class VETickTimerAnimatorPosition
: VETickTimerAnimatorBase() {

    var tickX = 0f
    var tickY = 0f

    override fun tick(
        tickTimeMs: Int,
        tickTimeFactor: Float
    ) {
        tickList.add(
            VETickDataPosition(
                tickTimeFactor,
                tickX,
                tickY
            )
        )
    }
}