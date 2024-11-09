package good.damn.editor.actions

import good.damn.sav.core.lists.VEListShapes

class VEDataActionShape(
    private val mShapes: VEListShapes
): VEIActionable {
    override fun removeAction() {
        mShapes.removeLast()
    }
}