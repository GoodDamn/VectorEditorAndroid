package good.damn.editor.vector.extensions.io

import good.damn.editor.vector.java.utils.VEUtilsIntJava
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