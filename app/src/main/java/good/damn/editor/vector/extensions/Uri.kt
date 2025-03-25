package good.damn.editor.vector.extensions

import android.net.Uri

val Uri.extension: String?
    get() = path?.substringAfterLast(".", "")