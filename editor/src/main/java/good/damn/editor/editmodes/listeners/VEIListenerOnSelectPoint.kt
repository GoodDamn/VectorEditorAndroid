package good.damn.editor.editmodes.listeners

import good.damn.sav.core.points.VEPointIndexed

interface VEIListenerOnSelectPoint {
    fun onSelectPoint(
        point: VEPointIndexed
    )
}