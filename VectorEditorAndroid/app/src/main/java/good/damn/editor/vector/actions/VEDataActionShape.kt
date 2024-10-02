package good.damn.editor.vector.actions

import good.damn.editor.vector.lists.VEListShapes

class VEDataActionShape(
    private val mShapes: VEListShapes
): VEIActionable {
    override fun removeAction() {
        mShapes.removeLastOrNull()
    }
}