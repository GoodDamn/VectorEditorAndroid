package good.damn.sav.core

import good.damn.sav.misc.Size
import java.io.OutputStream

interface VEIExportableKeyframe {
    fun export(
        os: OutputStream,
        canvasSize: Size
    )
}