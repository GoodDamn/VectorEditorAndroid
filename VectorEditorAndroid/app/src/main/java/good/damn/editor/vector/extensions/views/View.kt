package good.damn.editor.vector.extensions.views

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout

fun View.boundsFrame(
    top: Float = 0f,
    start: Float = 0f,
    bottom: Float = 0f,
    end: Float = 0f,
    width: Float = -2f,
    height: Float = -2f,
    gravity: Int = Gravity.START or Gravity.TOP
) {
    layoutParams = FrameLayout.LayoutParams(
        width.toInt(),
        height.toInt()
    ).apply {
        this.gravity = gravity
        topMargin = top.toInt()
        leftMargin = start.toInt()
        bottomMargin = bottom.toInt()
        rightMargin = end.toInt()
    }
}