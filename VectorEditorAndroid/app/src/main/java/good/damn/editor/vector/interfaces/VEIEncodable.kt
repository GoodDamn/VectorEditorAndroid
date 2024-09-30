package good.damn.editor.vector.interfaces

import java.io.OutputStream

interface VEIEncodable {
    fun onEncodeObject(
        os: OutputStream
    )
}