package good.damn.editor.vector.export

import good.damn.editor.vector.extensions.file.write
import good.damn.editor.vector.interfaces.Encodable
import good.damn.editor.vector.paints.VEPaintBase
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
            val vectorData = it.onEncodeObject()
            baos.write(
                vectorData.size
            )
            baos.write(
                vectorData
            )
        }

        val encodedData = baos.toByteArray()
        file.write(
            encodedData
        )
    }

}