package good.damn.sav.misc.extensions

inline fun minMax(
    vararg v: Float,
): Pair<Float, Float> {
    var max = 0f
    var min = Float.MAX_VALUE
    v.forEach {
        if (min > it) {
            min = it
        }

        if (it > max) {
            max = it
        }
    }

    return Pair(
        min,
        max
    )
}