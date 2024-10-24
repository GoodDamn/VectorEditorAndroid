package good.damn.editor.actions

import good.damn.sav.core.skeleton.VESkeleton2D

class VEDataActionSkeletonPoint(
    private val mSkeleton: VESkeleton2D
): VEIActionable {

    override fun removeAction() {
        mSkeleton.removeLast()
    }

}