package good.damn.editor.vector.view.gradient

import android.graphics.PointF

data class VEMGradientEdit(
    val from: PointF,
    val to: PointF,
    var colors: IntArray,
    var positions: FloatArray
)