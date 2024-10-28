package good.damn.sav.misc.extensions.primitives

fun Float.toDigitalFraction(
    int2: Float
) = (this / int2 * 255).toInt().toByte()

inline fun Float.isInRange(
    from: Float,
    to: Float
) = !(this < from || this > to)