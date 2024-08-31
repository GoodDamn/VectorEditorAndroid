package good.damn.editor.vector.extensions.io

import java.io.InputStream

fun InputStream.readU() = read() and 0xff

fun InputStream.readFraction() = readU() / 255f