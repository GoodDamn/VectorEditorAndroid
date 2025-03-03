package good.damn.sav.core

import java.io.OutputStream

interface VEIExportableAnimationEntity {
    val typeEntity: Byte

    fun writeId(
        os: OutputStream
    )
}