package good.damn.editor.vector.views

import android.content.Context
import android.graphics.Canvas
import android.view.View
import good.damn.editor.vector.views.animator.options.VEOptionAnimatorBase

class VEViewAnimator(
    context: Context
): View(
    context
) {

    var options: Array<
        VEOptionAnimatorBase
    >? = null

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(
            changed,
            left,
            top,
            right,
            bottom
        )

        var y = 0f
        val ww = width * 0.25f
        val hh = height * 0.1f

        options?.forEach {
            it.x = 0f
            it.y = y
            it.layout(
                ww, hh
            )
            y += hh
        }
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(canvas)
        options?.forEach {
            it.draw(canvas)
        }

    }

}