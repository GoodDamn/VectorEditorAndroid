package good.damn.editor.animation.animator.options.tickTimer

import android.util.Log
import good.damn.editor.animation.animator.options.tickTimer.base.VETickTimerAnimatorBase
import good.damn.editor.animation.animator.options.tickTimer.data.VETickDataPosition
import good.damn.editor.animation.animator.options.tickTimer.data.base.VETickData
import good.damn.editor.animation.animator.options.tickTimer.listeners.VEListenerOnTickPosition

class VETickTimerAnimatorPosition
: VETickTimerAnimatorBase() {

    companion object {
        private val TAG = VETickTimerAnimatorPosition::class.simpleName
    }

    var tickX = 0f
    var tickY = 0f

    var onTickPosition: VEListenerOnTickPosition? = null

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

    override fun interpolate(
        from: VETickData,
        to: VETickData,
        t: Float
    ) {
        from as VETickDataPosition
        to as VETickDataPosition

        onTickPosition?.onTickPosition(
            from.x + (to.x - from.x) * t,
            from.y + (to.y - from.y) * t
        )
    }

    override fun beginTickData() = VETickDataPosition(
        0.0f,
        0f,
        0f
    )
}