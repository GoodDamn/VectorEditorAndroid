package good.damn.editor.vector.views.animator.options

import good.damn.editor.vector.views.animator.options.base.VEOptionAnimatorBase
import good.damn.editor.vector.views.animator.options.tickTimer.VETickTimerAnimatorBase

data class VEOptionAnimatorData(
    val option: VEOptionAnimatorBase,
    val tickTimer: VETickTimerAnimatorBase
)