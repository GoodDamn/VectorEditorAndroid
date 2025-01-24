package good.damn.editor.animation.animator.options.canvas

import android.graphics.PointF
import good.damn.editor.animation.animator.options.canvas.keyframes.VECanvasOptionKeyframePosition
import good.damn.editor.animation.animator.options.canvas.previews.VECanvasOptionPreviewPosition
import good.damn.sav.core.animation.keyframe.VEIAnimationOption
import good.damn.sav.core.animation.keyframe.VEMKeyFrameDataPosition
import good.damn.sav.core.animation.keyframe.VEMKeyframe
import kotlin.math.abs

data class VEMAnimationOptionCanvasPosition(
    private val point: PointF, // this point can be deleted
    private val option: VEIAnimationOption,
    private val requester: VEIRequesterFloat
): VEIAnimationOptionCanvas,
VEITransactionReceiver {

    private val transaction = VETransactionKeyFrame(
        this
    )

    override val keyframe = VECanvasOptionKeyframePosition(
        option
    )

    override val preview = VECanvasOptionPreviewPosition(
        transaction
    )

    override fun onReceiveTransaction() {
        option.keyframes.add(
            VEMKeyframe(
                (requester.requestDataFloat() + abs(
                    keyframe.scrollX
                )) / option.duration,
                VEMKeyFrameDataPosition(
                    point.x,
                    point.y
                )
            )
        )
    }

}