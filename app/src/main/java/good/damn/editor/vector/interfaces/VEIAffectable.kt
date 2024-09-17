package good.damn.editor.vector.interfaces

import good.damn.editor.vector.paints.VEPaintBase

interface VEIAffectable {
    fun onAffect(
        affect: VEPaintBase
    )
}