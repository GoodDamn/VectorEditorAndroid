package good.damn.editor.views.animator.options.tickTimer

import android.graphics.Canvas
import good.damn.editor.interfaces.VEITickable

interface VETickTimerAnimator
: VEITickable {

    fun layoutGrid(
        width: Float,
        height: Float
    )

    fun drawGrid(
        canvas: Canvas
    )
}