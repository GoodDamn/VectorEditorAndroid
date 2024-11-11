package good.damn.editor.editmodes.animation

import androidx.annotation.ColorInt

interface VEIListenerOnChangeShape {
    fun changeShapeColor(
        @ColorInt color: Int
    )

    fun changeShapeWidth(
        v: Float
    )
}