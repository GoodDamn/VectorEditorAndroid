package good.damn.editor.vector.fragments

import androidx.annotation.ColorInt

interface VEColorPickable {
    fun pickColor(
        @ColorInt color: Int
    )
}