package good.damn.sav.core.animation.animators

import android.util.Log
import android.view.animation.AccelerateInterpolator
import android.view.animation.OvershootInterpolator
import good.damn.sav.core.animation.keyframe.VEMKeyframeColor
import good.damn.sav.core.animation.keyframe.VEMKeyframePosition
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.misc.extensions.interpolate
import good.damn.sav.misc.extensions.toInt32
import good.damn.sav.misc.utils.VEUtilsIntJava

class VEAnimatorColor(
    private val shape: VEShapeBase,
    list: List<VEMKeyframeColor>
): VEAnimatorBase<VEMKeyframeColor>(
    AccelerateInterpolator(),
    list
) {

    private val mTempColor = ByteArray(4)

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

        shape.color = mTempColor.toInt32()
    }
}