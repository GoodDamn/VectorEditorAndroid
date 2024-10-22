package good.damn.editor.vector.models

import good.damn.editor.vector.lists.VEListShapes
import good.damn.editor.vector.points.VEPointIndexed
import good.damn.editor.vector.shapes.VEShapeBase
import good.damn.editor.vector.skeleton.VESkeleton2D
import java.util.LinkedList

data class VEModelImport(
    val skeletonPoints: MutableList<VEPointIndexed>,
    val shapes: VEListShapes
)