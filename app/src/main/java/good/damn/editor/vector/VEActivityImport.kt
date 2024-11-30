package good.damn.editor.vector

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.importer.VEModelImport
import good.damn.editor.importer.VEViewAVS
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.sav.misc.Size

class VEActivityImport
: AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        val context = this

        FrameLayout(
            context
        ).apply {

            setBackgroundColor(
                0xff000315.toInt()
            )

            val canvasSize = Size(
                VEApp.width.toFloat(),
                VEApp.width.toFloat()
            )

            VEViewAVS(
                context
            ).apply {

                model = VEModelImport.createFromResource(
                    resources,
                    R.raw.avs_some,
                    canvasSize
                )

                addView(
                    this,
                    VEApp.width,
                    VEApp.width
                )
            }

            setContentView(
                this
            )
        }

    }

}