package good.damn.editor.animation.animator.options.canvas

import android.graphics.PointF
import good.damn.editor.animation.animator.options.canvas.keyframes.VECanvasOptionKeyframeBase
import good.damn.editor.animation.animator.options.canvas.previews.VECanvasOptionPreviewPosition
import good.damn.editor.transaction.VEIRequesterFloat
import good.damn.editor.transaction.VEITransactionReceiver
import good.damn.editor.transaction.VETransactionKeyFrame
import good.damn.sav.core.animation.animators.VEAnimatorPosition
import good.damn.sav.core.animation.keyframe.VEIAnimationOption
import good.damn.sav.core.animation.keyframe.VEIKeyframe
import good.damn.sav.core.animation.keyframe.VEMKeyframePosition
import good.damn.sav.misc.structures.tree.toList
import kotlin.math.abs

abstract class VEMAnimationOptionCanvasBase<T: VEIKeyframe>(
    protected val option: VEIAnimationOption<T>,
    protected val requester: VEIRequesterFloat
): VEIAnimationOptionCanvas {

    override val keyframe = VECanvasOptionKeyframeBase(
        option,
        requester
    )

    protected inline fun getFactor() = (
        requester.requestDataFloat() + abs(keyframe.scrollX)
    ) / option.duration
}