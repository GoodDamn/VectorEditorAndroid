package good.damn.editor.vector.files

import android.os.Environment
import android.util.Log
import java.io.File

class VEFileDocument(
    fileName: String
): File(
    Environment.getExternalStoragePublicDirectory(
        Environment.DIRECTORY_DOCUMENTS
    ),
    fileName
) {

    companion object {
        private const val TAG = "VEFileDocument"
    }

    init {
        if (!exists() && createNewFile()) {
            Log.d(TAG, "init: $fileName created")
        }
    }

}