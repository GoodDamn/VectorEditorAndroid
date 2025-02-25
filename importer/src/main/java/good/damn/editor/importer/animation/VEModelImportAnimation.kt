package good.damn.editor.importer.animation

import good.damn.editor.importer.VEModelImport

data class VEModelImportAnimation<T>(
    val static: VEModelImport,
    val animations: List<T>?
)