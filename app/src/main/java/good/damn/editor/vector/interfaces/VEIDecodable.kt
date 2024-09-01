package good.damn.editor.vector.interfaces

import java.io.InputStream

interface VEIDecodable {
    fun onDecodeObject(
        inp: InputStream
    )
}