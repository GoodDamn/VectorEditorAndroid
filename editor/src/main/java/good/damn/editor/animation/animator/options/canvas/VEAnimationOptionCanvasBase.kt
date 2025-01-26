package good.damn.editor.animation.animator.options.canvas

import good.damn.editor.animation.animator.options.canvas.keyframes.VECanvasOptionKeyframeBase
import good.damn.editor.transaction.VEIRequesterFloat
import good.damn.sav.core.VEMExportAnimation
import good.damn.sav.core.animation.keyframe.VEIAnimationOption
import good.damn.sav.core.animation.keyframe.VEIKeyframe
import kotlin.math.abs

abstract class VEAnimationOptionCanvasBase<T: VEIKeyframe>(
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