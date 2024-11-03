package good.damn.editor.vector.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import good.damn.editor.animator.options.VEOptionAnimatorColor
import good.damn.editor.animator.options.VEOptionAnimatorPosition
import good.damn.editor.animator.options.tickTimer.listeners.VEListenerOnTickColor
import good.damn.editor.vector.VEApp
import good.damn.editor.views.VEViewAnimatorEditor

class VEFragmentVectorAnimation
: Fragment(),
VEColorPickable {

    var onTickColorAnimation: VEListenerOnTickColor? = null
        set(v) {
            field = v
            mOptionColor.tickTimer.onTickColor = v
        }

    var onClickBtnPrev: View.OnClickListener? = null

    private val mOptionColor = VEOptionAnimatorColor()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (context == null) {
            return null
        }

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

        val editorAnimator = VEViewAnimatorEditor(
            context,
            0.35f,
            0.2f,
            0.25f
        ).apply {
            setBackgroundColor(0)

            options = arrayOf(
                mOptionColor,
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
                text = "<"

                setOnClickListener(
                    onClickBtnPrev
                )

                addView(
                    this,
                    -2,
                    -2
                )
            }

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

        return layout
    }

    override fun pickColor(
        color: Int
    ) {
        mOptionColor.tickTimer.color = color
    }

}