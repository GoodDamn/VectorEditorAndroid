package good.damn.sav.misc.interfaces

interface VEIComparable {
    fun compareMoreThan(
        with: VEIComparable
    ): Boolean
}