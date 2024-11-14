package good.damn.editor.animation.tickers

import good.damn.editor.animation.animator.options.tickTimer.listeners.VEListenerOnTickStrokeWidth
import good.damn.sav.core.shapes.VEShapeBase

class VETickerStrokeWidth(
    private val mShape: VEShapeBase
): VEListenerOnTickStrokeWidth {
    override fun onTickStrokeWidth(
        strokeWidthTick: Float
    ) {
        mShape.strokeWidth = strokeWidthTick
    }
}