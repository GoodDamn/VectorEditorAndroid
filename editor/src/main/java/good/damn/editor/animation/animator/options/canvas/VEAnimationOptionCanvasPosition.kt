package good.damn.editor.animation.animator.options.canvas

import android.graphics.PointF
import good.damn.editor.animation.animator.options.canvas.previews.VECanvasOptionPreviewPosition
import good.damn.editor.transaction.VEIRequesterFloat
import good.damn.editor.transaction.VEITransactionReceiver
import good.damn.editor.transaction.VETransactionKeyFrame
import good.damn.sav.core.VEMExportAnimation
import good.damn.sav.core.animation.animators.VEAnimatorPosition
import good.damn.sav.core.animation.keyframe.VEIAnimationOption
import good.damn.sav.core.animation.keyframe.VEMKeyframePosition
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.misc.structures.tree.toList

class VEAnimationOptionCanvasPosition(
    private val point: VEPointIndexed, // this point can be deleted
    option: VEIAnimationOption<VEMKeyframePosition>,
    requester: VEIRequesterFloat
): VEAnimationOptionCanvasBase<VEMKeyframePosition>(
    option,
    requester
), VEITransactionReceiver {

    override val preview = VECanvasOptionPreviewPosition(
        VETransactionKeyFrame(
            this
        )
    )

    override fun exportAnimation() = if (
        option.keyframes.size > 1
    ) VEMExportAnimation(
        point,
        0,
        option.keyframes.toList()
    ) else null

    override fun createAnimator() = if (
        option.keyframes.size > 1
    ) VEAnimatorPosition(
        point,
        option.keyframes.toList().iterator(),
        option.duration
    ) else null

    override fun onReceiveTransaction() = option.keyframes.add(
        VEMKeyframePosition(
            getFactor(),
            point.x,
            point.y
        )
    )

}