package good.damn.editor.editmodes.animation

import good.damn.editor.editmodes.listeners.VEIListenerOnChangeValueAnimation
import good.damn.sav.misc.interfaces.VEITouchable

interface VEIAnimatableEntity
: VEITouchable {

    var onChangeValueAnimation: VEIListenerOnChangeValueAnimation?

    fun createAnimationEntity(): VEEditAnimationEntity?

    fun getIdEntity(): Long
}