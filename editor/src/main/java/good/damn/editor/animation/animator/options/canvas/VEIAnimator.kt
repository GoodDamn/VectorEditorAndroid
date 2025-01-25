package good.damn.editor.animation.animator.options.canvas

import good.damn.sav.core.animation.animators.VEIListenerAnimation

interface VEIAnimator {
    fun createAnimator(): VEIListenerAnimation?
}