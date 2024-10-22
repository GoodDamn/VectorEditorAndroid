package good.damn.editor.vector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.vector.views.VEViewAnimator
import good.damn.editor.vector.views.animator.options.VEOptionAnimatorColor
import good.damn.editor.vector.views.animator.options.VEOptionAnimatorPositionX

class VEActivityAnimation
: AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        val context = this

        VEViewAnimator(
            context
        ).apply {
            options = arrayOf(
                VEOptionAnimatorColor(),
                VEOptionAnimatorPositionX()
            )

            setBackgroundColor(
                0xff000315.toInt()
            )

            setContentView(
                this
            )
        }
    }

}