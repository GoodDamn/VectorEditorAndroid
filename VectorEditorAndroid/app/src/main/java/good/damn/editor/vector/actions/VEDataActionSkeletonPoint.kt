package good.damn.editor.vector.actions

import good.damn.editor.vector.skeleton.VESkeleton2D

class VEDataActionSkeletonPoint(
    private val mSkeleton: VESkeleton2D
): VEIActionable {

    override fun removeAction() {
        mSkeleton.removeLast()
    }

}