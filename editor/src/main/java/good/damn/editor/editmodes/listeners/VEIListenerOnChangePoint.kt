package good.damn.editor.editmodes.listeners

import good.damn.editor.animation.VEPointIndexedAnimation
import good.damn.sav.core.points.VEPointIndexed

interface VEIListenerOnChangePoint {
    fun onChangePoint(
        point: VEPointIndexedAnimation
    )
}