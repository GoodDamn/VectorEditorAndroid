package good.damn.editor.animation.tickers

import good.damn.editor.animation.animator.options.VEOptionAnimatorColor
import good.damn.editor.animation.animator.options.tickTimer.listeners.VEListenerOnTickColor
import good.damn.sav.core.shapes.VEShapeBase

class VETickerColor(
    private val mShape: VEShapeBase
): VEListenerOnTickColor {
    override fun onTickColor(
        color: Int
    ) {
        mShape.color = color
    }
}