package good.damn.editor.vector.browsers.interfaces

import android.net.Uri

interface VEListenerOnGetBrowserContent {
    fun onGetBrowserContent(
        uri: Uri?
    )
}