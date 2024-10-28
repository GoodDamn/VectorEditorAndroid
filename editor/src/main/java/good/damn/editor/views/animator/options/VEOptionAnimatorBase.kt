package good.damn.editor.views.animator.options

import android.graphics.Canvas
import good.damn.editor.views.animator.options.tickTimer.base.VETickTimerAnimatorBase
import good.damn.editor.views.animator.options.tickTimer.data.base.VETickData
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VERectable

abstract class VEOptionAnimatorBase
: VERectable(),
VEIDrawable {
    abstract val tickTimer: VETickTimerAnimatorBase<VETickData>
}