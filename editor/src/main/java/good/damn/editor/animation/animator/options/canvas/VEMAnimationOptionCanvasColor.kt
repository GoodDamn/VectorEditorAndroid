package good.damn.editor.animation.animator.options.canvas

import good.damn.editor.animation.animator.options.canvas.previews.VECanvasOptionPreviewColor
import good.damn.editor.animation.animator.options.canvas.previews.VEICanvasOptionPreview
import good.damn.editor.transaction.VEIRequesterFloat
import good.damn.editor.transaction.VEITransactionReceiver
import good.damn.editor.transaction.VETransactionKeyFrame
import good.damn.sav.core.animation.animators.VEAnimatorColor
import good.damn.sav.core.animation.animators.VEIListenerAnimation
import good.damn.sav.core.animation.keyframe.VEIAnimationOption
import good.damn.sav.core.animation.keyframe.VEMKeyframeColor
import good.damn.sav.core.animation.keyframe.VEMKeyframePosition
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.misc.extensions.primitives.toByteArray
import good.damn.sav.misc.structures.tree.toList

class VEMAnimationOptionCanvasColor(
    private val shape: VEShapeBase,
    option: VEIAnimationOption<VEMKeyframeColor>,
    requester: VEIRequesterFloat
): VEMAnimationOptionCanvasBase<VEMKeyframeColor>(
    option,
    requester
), VEITransactionReceiver {

    override val preview = VECanvasOptionPreviewColor(
        VETransactionKeyFrame(
            this
        )
    )

    override fun createAnimator() = VEAnimatorColor(
        shape,
        option.keyframes.toList().iterator(),
        option.duration
    )

    override fun onReceiveTransaction() = option.keyframes.add(
        VEMKeyframeColor(
            getFactor(),
            shape.color.toByteArray()
        )
    )

}