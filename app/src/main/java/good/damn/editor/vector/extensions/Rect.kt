package good.damn.editor.vector.extensions

import android.graphics.Rect

inline fun Rect.containsX(
    v: Int
) = !(v < left || v > right)