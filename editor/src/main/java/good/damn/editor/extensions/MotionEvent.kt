package good.damn.editor.extensions

import android.view.MotionEvent

inline fun MotionEvent.getRawXIndexed(
    index: Int
) = getX(
    action shr MotionEvent.ACTION_POINTER_ID_SHIFT
)

inline fun MotionEvent.getRawYIndexed(
    index: Int
) = getY(
    action shr MotionEvent.ACTION_POINTER_ID_SHIFT
)