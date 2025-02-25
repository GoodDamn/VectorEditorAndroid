package good.damn.sav.core

import good.damn.sav.core.animation.keyframe.VEIKeyframe
import good.damn.sav.misc.structures.tree.BinaryTreeBalancer

class VEBinaryTreeBalancerFactor<T: VEIKeyframe>
: BinaryTreeBalancer<T> {

    override fun equals(
        v: T,
        vv: T
    ) = v.factor == vv.factor

    override fun moreThan(
        v: T,
        vv: T
    ) = v.factor > vv.factor

}