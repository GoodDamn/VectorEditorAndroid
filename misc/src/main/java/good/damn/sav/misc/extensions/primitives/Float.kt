package good.damn.sav.misc.extensions.primitives

fun Float.toDigitalFraction(
    int2: Float
) = (this / int2 * 255).toInt().toByte()