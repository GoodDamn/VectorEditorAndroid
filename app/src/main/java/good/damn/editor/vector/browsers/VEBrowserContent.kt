package good.damn.editor.vector.browsers

import android.app.Activity
import android.net.Uri
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.vector.browsers.interfaces.VEListenerOnGetBrowserContent
import java.lang.ref.WeakReference

class VEBrowserContent
: ActivityResultCallback<List<Uri>> {

    companion object {
        private const val ANY_TYPE = "*/*"
    }

    var onGetContent: VEListenerOnGetBrowserContent? = null

    private var mLauncher: ActivityResultLauncher<String>? = null

    fun register(
        activity: AppCompatActivity
    ) {
        mLauncher = activity.registerForActivityResult(
            ActivityResultContracts.GetMultipleContents(),
            this
        )
    }

    fun launch(
        mimeType: String = ANY_TYPE
    ) {
        mLauncher?.launch(
            mimeType
        )
    }

    override fun onActivityResult(
        result: List<Uri>
    ) {
        onGetContent?.onGetBrowserContent(
            result
        )
    }
}