package good.damn.editor.vector.view.gradient.interfaces

interface VEIListenerOnGradientShader {
    fun onGetGradientShader(
        colors: IntArray,
        positions: FloatArray
    )
}