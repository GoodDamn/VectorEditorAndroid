package good.damn.editor.animation.animator.options.tickTimer

import good.damn.editor.animation.animator.options.tickTimer.base.VETickTimerAnimatorBase
import good.damn.editor.animation.animator.options.tickTimer.data.VETickDataStrokeWidth
import good.damn.editor.animation.animator.options.tickTimer.data.base.VETickData
import good.damn.editor.animation.animator.options.tickTimer.listeners.VEListenerOnTickStrokeWidth

class VETickTimerAnimatorStrokeWidth
: VETickTimerAnimatorBase() {

    var onTickStrokeWidth: VEListenerOnTickStrokeWidth? = null

    var strokeWidth = 0f

    override fun beginTickData() = VETickDataStrokeWidth(
        0.0f,
        0.0f
    )

    override fun tick(
        tickTimeMs: Int,
        tickTimeFactor: Float
    ) {
        tickList.add(
            VETickDataStrokeWidth(
                tickTimeFactor,
                strokeWidth
            )
        )
    }

    override fun interpolate(
        from: VETickData,
        to: VETickData,
        t: Float
    ) {
        from as VETickDataStrokeWidth
        to as VETickDataStrokeWidth

        onTickStrokeWidth?.onTickStrokeWidth(
            from.strokeWidth + (
                to.strokeWidth - from.strokeWidth
            ) * t
        )
    }

}