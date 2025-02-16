package good.damn.editor.animation.animator.options.canvas

import good.damn.editor.animation.animator.options.canvas.previews.VECanvasOptionPreviewColor
import good.damn.editor.transaction.VEIRequesterFloat
import good.damn.editor.transaction.VEITransactionReceiver
import good.damn.editor.transaction.VETransactionKeyFrame
import good.damn.sav.core.VEMExportAnimation
import good.damn.sav.core.animation.animators.VEAnimatorInterpolation
import good.damn.sav.core.animation.interpolators.fill.VEAnimationInterpolatorFill
import good.damn.sav.core.animation.interpolators.fill.VEMFillColorPriority
import good.damn.sav.core.animation.keyframe.VEIAnimationOption
import good.damn.sav.core.animation.keyframe.fill.VEMKeyframeFill
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.fill.VEMFillColor
import good.damn.sav.misc.structures.tree.toList

class VEAnimationOptionCanvasFill(
    private val shape: VEShapeBase,
    option: VEIAnimationOption<VEMKeyframeFill>,
    requester: VEIRequesterFloat
): VEAnimationOptionCanvasBase<VEMKeyframeFill>(
    option,
    requester
), VEITransactionReceiver {

    override val preview = VECanvasOptionPreviewColor(
        VETransactionKeyFrame(
            this
        )
    )

    override fun exportAnimation() = if (
        option.keyframes.size > 1
    ) VEMExportAnimation(
        shape,
        0,
        option.keyframes.toList()
    ) else null


    override fun createAnimator() = option.keyframes.convertToInterpolators { start, end ->
        VEAnimationInterpolatorFill(
            start,
            end,
            shape
        )
    }?.run {
        VEAnimatorInterpolation(
            this
        ).apply {
            duration = option.duration
        }
    }

    override fun onReceiveTransaction() {
        val fill = shape.fill
            ?: return

        option.keyframes.add(
            VEMKeyframeFill(
                getFactor(),
                fill.copy(),
                fill.createPriority()
            )
        )
    }

}