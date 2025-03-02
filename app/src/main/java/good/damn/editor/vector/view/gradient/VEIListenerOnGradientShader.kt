package good.damn.editor.vector.view.gradient

import android.graphics.LinearGradient

interface VEIListenerOnGradientShader {
    fun onGetGradientShader(
        colors: IntArray,
        positions: FloatArray
    )
}