package good.damn.sav.core

import java.io.OutputStream

data class VEMIdentifier(
    val id: Int,
    val offset: Int
) {

    companion object {
        val ZERO = VEMIdentifier(
            0,
            0
        )
    }

    val normalized = (id shr offset) and 0xff

    fun write(
        os: OutputStream
    ) = os.write(normalized)


    override fun equals(
        other: Any?
    ): Boolean {

        if (other !is VEMIdentifier) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}