package good.damn.sav.misc.extensions

import good.damn.sav.misc.utils.VEUtilsByteArrayJava
import good.damn.sav.misc.utils.VEUtilsIntJava

inline fun ByteArray.interpolate(
    v1: ByteArray,
    v2: ByteArray,
    t: Float
) = VEUtilsByteArrayJava.interpolate(
    v1,
    v2,
    t,
    this
)

inline fun ByteArray.toInt32() = VEUtilsIntJava
    .int32(this)