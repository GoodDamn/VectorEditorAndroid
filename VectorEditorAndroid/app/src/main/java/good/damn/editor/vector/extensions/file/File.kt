package good.damn.editor.vector.extensions.file

import androidx.annotation.WorkerThread
import java.io.File
import java.io.FileOutputStream

@WorkerThread
fun File.write(
    data: ByteArray
) {
    if (!exists()) {
        return
    }

    val fos = FileOutputStream(
        this
    )
    fos.write(data)
    fos.close()
}