package good.damn.editor.vector

import android.app.Application

class VEApp
: Application() {

    companion object {
        var width = 0
        var height = 0
    }

    override fun onCreate() {
        super.onCreate()

        resources.displayMetrics.apply {
            width = widthPixels
            height = heightPixels
        }

    }

}