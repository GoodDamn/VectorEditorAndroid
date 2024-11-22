package good.damn.sav.misc

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
        if (!exists() && createNewFile()) {
            Log.d(TAG, "init: $name is created")
        }
    }

}