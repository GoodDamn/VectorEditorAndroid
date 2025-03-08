package good.damn.editor.editmodes.listeners

interface VEIListenerOnTransform {
    fun onScale(
        delta: Float
    )

    fun onTranslate(
        x: Float,
        y: Float
    )

}