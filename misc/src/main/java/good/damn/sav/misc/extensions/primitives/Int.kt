package good.damn.sav.misc.extensions.primitives

import good.damn.sav.misc.utils.VEUtilsIntJava


inline fun Int.toByteArray(): ByteArray = VEUtilsIntJava.int32(
    this
)