package good.damn.editor.vector.view.gradient.interfaces

import android.graphics.PointF

interface VEIListenerOnGradientPosition {
    fun onGetGradientPosition(
        from: PointF,
        to: PointF
    )
}