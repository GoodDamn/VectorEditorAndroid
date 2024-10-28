package good.damn.editor.vector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.views.VEViewAnimator
import good.damn.editor.animator.options.VEOptionAnimatorColor
import good.damn.editor.animator.options.VEOptionAnimatorPosition

class VEActivityAnimation
: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        VEViewAnimator(
            this,
            0.1f,
            0.2f
        ).apply {

            setBackgroundColor(
                0xff000315.toInt()
            )

            options = arrayOf(
                VEOptionAnimatorColor(),
                VEOptionAnimatorPosition()
            )

            setContentView(
                this
            )
        }
    }

}