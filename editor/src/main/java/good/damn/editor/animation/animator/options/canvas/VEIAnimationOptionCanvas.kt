package good.damn.editor.animation.animator.options.canvas

import good.damn.editor.animation.animator.VEIExportableAnimation
import good.damn.editor.animation.animator.options.canvas.keyframes.VEICanvasOptionKeyframe
import good.damn.editor.animation.animator.options.canvas.previews.VEICanvasOptionPreview

interface VEIAnimationOptionCanvas
: VEIAnimator,
VEIExportableAnimation {
    val preview: VEICanvasOptionPreview
    val keyframe: VEICanvasOptionKeyframe
}