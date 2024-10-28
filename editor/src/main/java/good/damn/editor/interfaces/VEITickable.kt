package good.damn.editor.interfaces

import good.damn.sav.misc.structures.BinaryTree

interface VEITickable {
    fun tick(
        tickTimeMs: Int,
        tickTimeFactor: Float
    )
}