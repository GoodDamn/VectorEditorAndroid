package good.damn.editor.animation.animator.options.tickTimer.data

import androidx.annotation.ColorInt
import good.damn.editor.animation.animator.options.tickTimer.data.base.VETickData
import good.damn.editor.animation.animator.options.tickTimer.data.base.VETickDataBase
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.misc.interfaces.VEIComparable

data class VETickDataColor(
    override val tickFactor: Float,
    val argb: ByteArray
): VETickDataBase()