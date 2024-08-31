package good.damn.editor.vector.interfaces

import java.io.InputStream

interface Decodable {
    fun onDecodeObject(
        inp: InputStream
    )
}