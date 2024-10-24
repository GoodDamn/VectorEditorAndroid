package good.damn.sav.misc.extensions.io

import good.damn.sav.misc.utils.VEUtilsIntJava
import java.io.InputStream

fun InputStream.readU() = read() and 0xff

fun InputStream.readFraction() = readU() / 255f

fun InputStream.readInt32(
    buffer: ByteArray
): Int {
    read(buffer, 0, 4)
    return VEUtilsIntJava.int32(
        buffer
    )
}