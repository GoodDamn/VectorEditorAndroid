package good.damn.editor.animation.animator.options.canvas.keyframes

import good.damn.sav.core.animation.VEMKeyFrame
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VEILayoutable

interface VEICanvasOptionKeyFrame
: VEILayoutable,
VEIDrawable {
    fun addKeyFrame(
        keyFrame: VEMKeyFrame
    )
}