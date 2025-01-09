package good.damn.editor.vector

import android.os.Environment
import android.util.Log
import java.io.File

class VEFile(
    name: String
): File(
    Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_DOCUMENTS
    ),
    name
) {

    companion object {
        private val TAG = VEFile::class.simpleName
    }

    init {
        if (parentFile?.exists() == false) {
            parentFile?.mkdirs()
        }

        if (!exists() && createNewFile()) {
            Log.d(TAG, "init: $name is created")
        }
    }

}