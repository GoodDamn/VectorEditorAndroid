package good.damn.sav.misc.structures.tree

interface BinaryTreeBalancer<T> {
    fun equals(
        v: T,
        vv: T
    ): Boolean

    fun moreThan(
        v: T,
        vv: T
    ): Boolean
}