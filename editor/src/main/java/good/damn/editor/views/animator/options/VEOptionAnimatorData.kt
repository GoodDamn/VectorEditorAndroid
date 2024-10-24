package good.damn.editor.views.animator.options

import good.damn.editor.views.animator.options.base.VEOptionAnimatorBase
import good.damn.editor.views.animator.options.tickTimer.VETickTimerAnimatorBase

data class VEOptionAnimatorData(
    val option: VEOptionAnimatorBase,
    val tickTimer: VETickTimerAnimatorBase
)