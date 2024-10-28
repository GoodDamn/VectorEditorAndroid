package good.damn.editor.animator.options.tickTimer.data.base

import good.damn.sav.misc.interfaces.VEIComparable

abstract class VETickDataBase
: VETickData {

    override fun compareMoreThan(
        with: VEIComparable
    ) = tickFactor > (
        (with as? VETickDataBase)
            ?.tickFactor ?: 0.0f
    )

    override fun equals(
        other: Any?
    ) = (other as? VETickDataBase)
        ?.tickFactor == tickFactor

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

}