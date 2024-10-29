package good.damn.editor.vector

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.views.VEViewAnimator
import good.damn.editor.animator.options.VEOptionAnimatorColor
import good.damn.editor.animator.options.VEOptionAnimatorPosition

class VEActivityAnimation
: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(
            this
        ).apply {
            setBackgroundColor(
                0xff000315.toInt()
            )
        }

        VEViewAnimator(
            this,
            0.35f,
            0.2f,
            0.25f
        ).apply {

            setBackgroundColor(0)

            options = arrayOf(
                VEOptionAnimatorColor(),
                VEOptionAnimatorPosition()
            )

            layout.addView(
                this,
                -1,
                (VEApp.height * 0.3f).toInt()
            )
        }


        setContentView(
            layout
        )
    }

}