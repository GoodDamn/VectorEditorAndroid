package good.damn.editor.animation.animator.options.canvas

import good.damn.editor.animation.animator.options.canvas.keyframes.VEICanvasOptionKeyframe
import good.damn.editor.animation.animator.options.canvas.previews.VEICanvasOptionPreview

interface VEIAnimationOptionCanvas
: VEIAnimator {
    val preview: VEICanvasOptionPreview
    val keyframe: VEICanvasOptionKeyframe
}