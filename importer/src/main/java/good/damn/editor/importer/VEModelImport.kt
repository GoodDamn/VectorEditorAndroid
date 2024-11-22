package good.damn.editor.importer

import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.skeleton.VESkeleton2D

data class VEModelImport(
    val skeleton: VESkeleton2D,
    val shapes: VEListShapes
)