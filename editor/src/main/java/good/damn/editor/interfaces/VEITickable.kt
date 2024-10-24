package good.damn.editor.interfaces

interface VEITickable {
    fun tick(
        tickTimeMs: Int,
        tickTimeFactor: Float
    )
}