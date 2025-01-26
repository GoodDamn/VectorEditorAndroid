package good.damn.editor.animation.animator

import good.damn.sav.core.VEMExportAnimation

interface VEIExportableAnimation {
    fun exportAnimation(): VEMExportAnimation?
}