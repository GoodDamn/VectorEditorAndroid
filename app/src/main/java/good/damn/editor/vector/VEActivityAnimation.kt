package good.damn.editor.vector

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.views.VEViewAnimatorEditor
import good.damn.editor.animator.options.VEOptionAnimatorColor
import good.damn.editor.animator.options.VEOptionAnimatorPosition
import good.damn.editor.animator.options.tickTimer.listeners.VEListenerOnTickColor
import good.damn.editor.animator.options.tickTimer.listeners.VEListenerOnTickPosition
import good.damn.gradient_color_picker.GradientColorPicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VEActivityAnimation
: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this

        val optionColor = VEOptionAnimatorColor()
        val optionPosition = VEOptionAnimatorPosition()

        val layout = LinearLayout(
            context
        ).apply {
            orientation = LinearLayout
                .VERTICAL
            setBackgroundColor(
                0xff000315.toInt()
            )
        }

        View(
            context
        ).apply {

            val scope = CoroutineScope(
                Dispatchers.Main
            )

            optionColor.tickTimer.onTickColor = object: VEListenerOnTickColor {
                override fun onTickColor(
                    color: Int
                ) {
                    scope.launch {
                        setBackgroundColor(color)
                    }
                }
            }


            layout.addView(
                this,
                -1,
                100
            )
        }

        val editorAnimator = VEViewAnimatorEditor(
            context,
            0.35f,
            0.2f,
            0.25f
        ).apply {
            setBackgroundColor(0)

            options = arrayOf(
                optionColor,
                optionPosition
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

        GradientColorPicker(
            context
        ).apply {

            setOnPickColorListener {
                optionColor.tickTimer.color = it
            }

            layout.addView(
                this,
                -1,
                (VEApp.height * 0.2f).toInt()
            )
        }

        setContentView(
            layout
        )
    }

}