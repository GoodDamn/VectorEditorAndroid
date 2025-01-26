package good.damn.sav.misc.extensions.io

import java.io.OutputStream

inline fun OutputStream.write(
    b: Byte
) {
    write(
        b.toInt()
    )
}