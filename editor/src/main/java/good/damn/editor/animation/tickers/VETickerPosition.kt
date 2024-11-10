package good.damn.editor.animation.tickers

import good.damn.editor.animation.animator.options.tickTimer.listeners.VEListenerOnTickPosition
import good.damn.sav.core.points.VEPointIndexed

class VETickerPosition(
    private val point: VEPointIndexed
): VEListenerOnTickPosition {
    override fun onTickPosition(
        x: Float,
        y: Float
    ) = point.set(x,y)
}