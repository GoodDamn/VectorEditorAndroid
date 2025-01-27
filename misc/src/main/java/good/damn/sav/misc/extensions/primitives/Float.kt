package good.damn.sav.misc.extensions.primitives

import good.damn.sav.misc.utils.VEUtilsFloatJava

inline fun Float.toDigitalFraction(
    int2: Float
) = VEUtilsFloatJava.toDigitalFraction(
    this,
    int2
)

inline fun Float.toDigitalFraction(
) = VEUtilsFloatJava.getDigitalFraction(
    this
)