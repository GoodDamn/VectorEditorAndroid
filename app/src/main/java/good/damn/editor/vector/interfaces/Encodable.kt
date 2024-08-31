package good.damn.editor.vector.interfaces

import java.io.OutputStream

interface Encodable {
    fun onEncodeObject(
        os: OutputStream
    )
}