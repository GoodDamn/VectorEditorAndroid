package good.damn.editor.interfaces

import good.damn.editor.animation.animator.options.tickTimer.data.base.VETickData

interface VEIInterpolatable {
    fun interpolate(
        from: VETickData,
        to: VETickData,
        t: Float
    )
}