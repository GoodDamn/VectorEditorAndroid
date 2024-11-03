package good.damn.editor.vector.interfaces

import androidx.annotation.ColorInt

interface VEIColorPickable {
    fun pickColor(
        @ColorInt color: Int
    )
}