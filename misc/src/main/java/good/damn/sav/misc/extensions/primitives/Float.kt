package good.damn.sav.misc.extensions.primitives

inline fun Float.toDigitalFraction(
    int2: Float
) = (this / int2 * 255).toInt().toByte()

inline fun Float.toDigitalFraction() = (
    this * 255
).toInt()

inline fun Float.isInRange(
    from: Float,
    to: Float
) = !(this < from || this > to)