package good.damn.editor.views.animator.options.base

import android.graphics.Canvas

interface VEOptionAnimator {

    fun layout(
        width: Float,
        height: Float
    )

    fun draw(
        canvas: Canvas
    )
}