package good.damn.editor.vector.interfaces

interface VEITouchable {
    fun onDown(
        x: Float,
        y: Float
    )
    fun onMove(
        moveX: Float,
        moveY: Float
    )
    fun onUp(
        x: Float,
        y: Float
    )
}