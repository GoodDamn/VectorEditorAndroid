package good.damn.editor.animation.animator.options

import android.graphics.Canvas
import good.damn.editor.animation.animator.options.tickTimer.base.VETickTimerAnimatorBase
import good.damn.editor.animation.animator.options.tickTimer.data.base.VETickData
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VERectable

abstract class VEOptionAnimatorBase
: VERectable(),
VEIDrawable {
    abstract val tickTimer: VETickTimerAnimatorBase
}