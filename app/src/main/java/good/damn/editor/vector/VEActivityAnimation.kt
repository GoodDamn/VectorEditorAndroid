package good.damn.editor.vector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.editor.views.animator.VEViewAnimator
import good.damn.editor.views.animator.options.VEOptionAnimatorColor
import good.damn.editor.views.animator.options.VEOptionAnimatorData
import good.damn.editor.views.animator.options.tickTimer.VETickTimerAnimatorColor

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
                VEOptionAnimatorData(
                    VEOptionAnimatorColor(),
                    VETickTimerAnimatorColor()
                )
            )

            setContentView(
                this
            )
        }
    }

}