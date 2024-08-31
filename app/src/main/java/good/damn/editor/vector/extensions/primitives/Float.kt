package good.damn.editor.vector.extensions.primitives

fun Float.toDigitalFraction(
    int2: Float
) = (this / int2 * 255).toInt().toByte()