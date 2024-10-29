package good.damn.editor.vector

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.views.VEViewAnimator
import good.damn.editor.animator.options.VEOptionAnimatorColor
import good.damn.editor.animator.options.VEOptionAnimatorPosition

class VEActivityAnimation
: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this

        val layout = LinearLayout(
            context
        ).apply {
            orientation = LinearLayout
                .VERTICAL
            setBackgroundColor(
                0xff000315.toInt()
            )
        }

        val editorAnimator = VEViewAnimator(
            context,
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

        LinearLayout(
            context
        ).apply {
            Button(
                context
            ).apply {

                text = "Play"

                setOnClickListener {
                    editorAnimator.play()
                }

                addView(
                    this,
                    -2,
                    -2
                )
            }

            Button(
                context
            ).apply {

                text = "Pause"

                setOnClickListener {
                    editorAnimator.pause()
                }

                addView(
                    this
                )
            }


            layout.addView(
                this,
                -1,
                -2
            )
        }



        setContentView(
            layout
        )
    }

}