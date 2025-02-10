package good.damn.sav.core.animation.animators

import android.util.Log
import android.view.animation.AccelerateInterpolator
import good.damn.sav.core.animation.keyframe.VEMKeyframeColor
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.fill.VEMFillColor
import good.damn.sav.misc.extensions.interpolate
import good.damn.sav.misc.extensions.toInt32

class VEAnimatorColor(
    private val shape: VEShapeBase,
    list: List<VEMKeyframeColor>
): VEAnimatorBase<VEMKeyframeColor>(
    AccelerateInterpolator(),
    list
) {

    private val mTempColor = ByteArray(4)
    private val mFillColor = VEMFillColor(0)

    override fun onNextFrame(
        start: VEMKeyframeColor,
        end: VEMKeyframeColor,
        factor: Float
    ) {
        mTempColor.interpolate(
            start.color,
            end.color,
            factor
        )
        Log.d("VEAnimatorColor:", "onNextFrame: $factor")

        mFillColor.color = mTempColor.toInt32()
        shape.fill = mFillColor
    }
}