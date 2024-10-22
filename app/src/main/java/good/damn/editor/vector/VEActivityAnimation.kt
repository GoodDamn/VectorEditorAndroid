package good.damn.editor.vector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.vector.views.animator.VEViewAnimator
import good.damn.editor.vector.views.animator.options.VEOptionAnimatorColor
import good.damn.editor.vector.views.animator.options.VEOptionAnimatorData
import good.damn.editor.vector.views.animator.options.VEOptionAnimatorPositionX
import good.damn.editor.vector.views.animator.options.tickTimer.VETickTimerAnimatorColor

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
            context,
            0.1f,
            0.25f
        ).apply {
            options = arrayOf(
                VEOptionAnimatorData(
                    VEOptionAnimatorColor(),
                    VETickTimerAnimatorColor()
                )
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