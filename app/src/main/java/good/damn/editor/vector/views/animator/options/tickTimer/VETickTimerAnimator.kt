package good.damn.editor.vector.views.animator.options.tickTimer

import android.graphics.Canvas

interface VETickTimerAnimator {

    fun layoutGrid(
        width: Float,
        height: Float
    )

    fun drawGrid(
        canvas: Canvas
    )
}