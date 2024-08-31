package good.damn.editor.vector.extensions.io

import java.io.OutputStream

fun OutputStream.write(
    b: Byte
) {
    write(
        b.toInt()
    )
}