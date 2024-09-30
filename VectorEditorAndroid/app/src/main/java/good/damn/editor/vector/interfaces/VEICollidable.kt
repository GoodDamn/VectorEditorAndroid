package good.damn.editor.vector.interfaces

interface VEICollidable {
    fun onCheckCollision(
        px: Float,
        py: Float
    ): Boolean
}