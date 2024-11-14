package good.damn.editor.animation.animator.options.tickTimer.data

import good.damn.editor.animation.animator.options.tickTimer.data.base.VETickData
import good.damn.editor.animation.animator.options.tickTimer.data.base.VETickDataBase

data class VETickDataStrokeWidth(
    override val tickFactor: Float,
    val strokeWidth: Float
): VETickDataBase()