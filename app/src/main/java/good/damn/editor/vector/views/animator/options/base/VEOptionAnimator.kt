package good.damn.editor.vector.views.animator.options.base

import android.graphics.Canvas
import good.damn.editor.vector.interfaces.VEITickable
import good.damn.editor.vector.interfaces.VEITouchable

interface VEOptionAnimator {

    fun layout(
        width: Float,
        height: Float
    )

    fun draw(
        canvas: Canvas
    )
}