package good.damn.editor.vector.options

import android.graphics.Canvas
import good.damn.editor.vector.lists.VEListShapes
import good.damn.editor.vector.points.VEPointIndexed
import good.damn.editor.vector.skeleton.VESkeleton2D

class VEOptionFreeMove
: VEIOptionable {

    override fun runOption(
        shapes: VEListShapes,
        selectedPoint: VEPointIndexed?,
        skeleton: VESkeleton2D
    ) = Unit

    override fun onClear() = Unit
}