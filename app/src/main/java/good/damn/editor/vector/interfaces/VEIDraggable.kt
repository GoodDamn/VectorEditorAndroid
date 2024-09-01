package good.damn.editor.vector.interfaces

interface VEIDraggable {
    fun onDragVector(
        touchX: Float,
        touchY: Float
    ): Boolean

    fun onDragMove(
        x: Float,
        y: Float
    )
}