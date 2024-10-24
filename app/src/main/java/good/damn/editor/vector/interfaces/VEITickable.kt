package good.damn.editor.vector.interfaces

interface VEITickable {
    fun tick(
        tickTimeMs: Int,
        tickTimeFactor: Float
    )
}