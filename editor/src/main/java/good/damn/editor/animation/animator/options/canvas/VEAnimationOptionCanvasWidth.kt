package good.damn.editor.animation.animator.options.canvas

import good.damn.editor.animation.animator.options.canvas.previews.VECanvasOptionPreviewWidth
import good.damn.editor.animation.animator.options.canvas.previews.VEICanvasOptionPreview
import good.damn.editor.transaction.VEIRequesterFloat
import good.damn.editor.transaction.VEITransactionReceiver
import good.damn.editor.transaction.VETransactionKeyFrame
import good.damn.sav.core.VEMExportAnimation
import good.damn.sav.core.animation.animators.VEAnimatorBase
import good.damn.sav.core.animation.animators.VEIListenerAnimation
import good.damn.sav.core.animation.interpolators.VEAnimationInterpolatorStrokeWidth
import good.damn.sav.core.animation.keyframe.VEIAnimationOption
import good.damn.sav.core.animation.keyframe.VEMKeyframeWidth
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.misc.structures.tree.toList

class VEAnimationOptionCanvasWidth(
    private val shape: VEShapeBase,
    option: VEIAnimationOption<VEMKeyframeWidth>,
    requester: VEIRequesterFloat
): VEAnimationOptionCanvasBase<VEMKeyframeWidth>(
    option,
    requester
), VEITransactionReceiver {

    override val preview = VECanvasOptionPreviewWidth(
        VETransactionKeyFrame(
            this
        )
    )

    override fun exportAnimation() = if (
        option.keyframes.size > 1
    ) VEMExportAnimation(
        shape,
        1,
        option.keyframes.toList()
    ) else null

    override fun createAnimator() = option.keyframes.convertToInterpolators {
        start, end ->
        VEAnimationInterpolatorStrokeWidth(
            start,
            end,
            shape
        )
    }?.run {
        VEAnimatorBase(
            this
        ).apply {
            duration = option.duration
        }
    }

    override fun onReceiveTransaction() = option.keyframes.add(
        VEMKeyframeWidth(
            getFactor(),
            shape.strokeWidth
        )
    )

}