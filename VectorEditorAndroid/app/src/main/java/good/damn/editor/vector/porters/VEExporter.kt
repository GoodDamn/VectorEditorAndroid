package good.damn.editor.vector.porters

import android.util.Log
import good.damn.editor.vector.extensions.file.write
import good.damn.editor.vector.extensions.writeToStream
import good.damn.editor.vector.interfaces.VEIEncodable
import good.damn.editor.vector.skeleton.VESkeleton2D
import java.io.ByteArrayOutputStream
import java.io.File

class VEExporter {

    companion object {
        private const val mVersionExporter = 1
        private const val TAG = "VEExporter"
    }

    fun exportTo(
        file: File,
        shapes: List<VEIEncodable>,
        skeleton: VESkeleton2D,
        canvasWidth: Float,
        canvasHeight: Float
    ) {
        val baos = ByteArrayOutputStream()
        baos.write(mVersionExporter)
        baos.write(skeleton.size)

        skeleton.forEachh {
            Log.d(TAG, "exportTo: POINT: $it")
            it.writeToStream(
                baos,
                canvasWidth,
                canvasHeight
            )
        }

        baos.write(
            shapes.size
        )

        shapes.forEach {
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