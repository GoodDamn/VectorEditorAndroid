package good.damn.sav.misc.extensions

import good.damn.sav.misc.utils.VEUtilsByteArrayJava

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
