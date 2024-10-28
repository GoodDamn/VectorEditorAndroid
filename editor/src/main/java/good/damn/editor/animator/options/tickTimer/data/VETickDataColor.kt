package good.damn.editor.animator.options.tickTimer.data

import androidx.annotation.ColorInt
import good.damn.editor.animator.options.tickTimer.data.base.VETickData
import good.damn.editor.animator.options.tickTimer.data.base.VETickDataBase
import good.damn.sav.misc.interfaces.VEIComparable

data class VETickDataColor(
    override val tickFactor: Float,
    @ColorInt val color: Int
): VETickDataBase()