package good.damn.sav.misc.extensions.primitives

import good.damn.sav.misc.utils.VEUtilsFloatJava

inline fun Float.interpolate(
    end: Float,
    factor: Float
) = this + (end - this) * factor

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