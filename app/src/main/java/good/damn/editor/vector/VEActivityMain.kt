package good.damn.editor.vector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.vector.views.VEViewVector

class VEActivityMain
: AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        val context = this

        setContentView(
            VEViewVector(
                context
            )
        )

    }

}