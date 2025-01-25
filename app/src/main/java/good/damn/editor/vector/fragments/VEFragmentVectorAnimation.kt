package good.damn.editor.vector.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import good.damn.editor.vector.VEFragmentVectorProcesser
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.interfaces.VEIColorPickable
import good.damn.editor.views.VEViewAnimatorEditor
import good.damn.sav.core.animation.animators.VEIListenerAnimationUpdateFrame

class VEFragmentVectorAnimation
: Fragment(),
VEIColorPickable {

    val processer = VEFragmentVectorProcesser()

    var onClickBtnPrev: View.OnClickListener? = null

    var onUpdateFrameAnimation: VEIListenerAnimationUpdateFrame? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (context == null) {
            return null
        }

        val layout = LinearLayout(
            context
        ).apply {
            orientation = LinearLayout
                .VERTICAL
            setBackgroundColor(
                0xff000315.toInt()
            )
        }

        VEViewAnimatorEditor(
            context
        ).apply {
            processer.viewAnimatorEditor = this
            onUpdateFrameAnimation = this@VEFragmentVectorAnimation
                .onUpdateFrameAnimation
            setBackgroundColor(0)
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
                    processer.play()
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
                    processer.pause()
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

    }

}