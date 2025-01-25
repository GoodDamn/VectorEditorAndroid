package good.damn.editor.animation.animator.options.canvas

import android.graphics.PointF
import good.damn.editor.animation.animator.options.canvas.keyframes.VECanvasOptionKeyframeBase
import good.damn.editor.animation.animator.options.canvas.previews.VECanvasOptionPreviewBase
import good.damn.editor.animation.animator.options.canvas.previews.VECanvasOptionPreviewPosition
import good.damn.sav.core.animation.animators.VEAnimatorPosition
import good.damn.sav.core.animation.keyframe.VEIAnimationOption
import good.damn.sav.core.animation.keyframe.VEMKeyframePosition
import good.damn.sav.misc.structures.tree.toList
import kotlin.math.abs

data class VEMAnimationOptionCanvasPosition(
    private val point: PointF, // this point can be deleted
    private val option: VEIAnimationOption<VEMKeyframePosition>,
    private val requester: VEIRequesterFloat
): VEIAnimationOptionCanvas,
VEITransactionReceiver {

    private val transaction = VETransactionKeyFrame(
        this
    )

    override val keyframe = VECanvasOptionKeyframeBase(
        option
    )

    override val preview = VECanvasOptionPreviewPosition(
        transaction
    )

    override fun createAnimator() = if (
        option.keyframes.size > 1
    ) VEAnimatorPosition(
        point,
        option.keyframes.toList().iterator(),
        option.duration
    ) else null

    override fun onReceiveTransaction() {
        option.keyframes.add(
            VEMKeyframePosition(
                (requester.requestDataFloat() + abs(
                    keyframe.scrollX
                )) / option.duration,
                point.x,
                point.y
            )
        )
    }

}