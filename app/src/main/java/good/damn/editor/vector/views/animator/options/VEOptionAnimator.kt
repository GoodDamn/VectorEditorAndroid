package good.damn.editor.vector.views.animator.options

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