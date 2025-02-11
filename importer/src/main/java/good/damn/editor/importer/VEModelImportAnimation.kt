package good.damn.editor.importer

import good.damn.sav.core.animation.animators.VEIListenerAnimation

data class VEModelImportAnimation(
    val static: VEModelImport,
    val animations: List<VEIListenerAnimation>?
)