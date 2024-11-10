package good.damn.editor.editmodes.listeners

import good.damn.editor.animation.VEAnimationEntity

interface VEIListenerOnChangeValueAnimation {
    fun onChangeValueAnimation(
        entity: VEAnimationEntity,
        value: Any
    )
}