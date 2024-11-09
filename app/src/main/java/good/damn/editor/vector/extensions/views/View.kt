package good.damn.editor.vector.extensions.views

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout

fun View.bounds(
    width: Float,
    height: Float
) {
    layoutParams = ViewGroup.LayoutParams(
        width.toInt(),
        height.toInt()
    )
}

fun View.boundsLinear(
    top: Float = 0f,
    start: Float = 0f,
    bottom: Float = 0f,
    end: Float = 0f,
    width: Float = -2f,
    height: Float = -2f,
    gravity: Int = Gravity.START or Gravity.TOP
) {
    layoutParams = LinearLayout.LayoutParams(
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