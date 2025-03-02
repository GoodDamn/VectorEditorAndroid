package good.damn.editor.vector.view.gradient

import android.graphics.PointF

interface VEIListenerOnGradientPosition {
    fun onGetGradientPosition(
        from: PointF,
        to: PointF
    )
}