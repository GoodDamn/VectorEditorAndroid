package good.damn.editor.vector.porters

import good.damn.editor.vector.extensions.file.write
import good.damn.editor.vector.interfaces.Encodable
import java.io.ByteArrayOutputStream
import java.io.File

class VEExporter {

    companion object {
        private const val mVersionExporter = 1
    }

    fun exportTo(
        file: File,
        inputData: List<Encodable>
    ) {
        val baos = ByteArrayOutputStream()
        baos.write(mVersionExporter)
        baos.write(inputData.size)

        inputData.forEach {
            it.onEncodeObject(
                baos
            )
        }

        val encodedData = baos.toByteArray()
        file.write(
            encodedData
        )
    }

}