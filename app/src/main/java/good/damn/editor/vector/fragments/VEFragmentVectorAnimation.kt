package good.damn.editor.vector.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import good.damn.editor.editmodes.animation.VEEditAnimationEntity
import good.damn.editor.animation.animator.options.VEOptionAnimatorBase
import good.damn.editor.animation.animator.options.VEOptionAnimatorColor
import good.damn.editor.animation.animator.options.VEOptionAnimatorPosition
import good.damn.editor.editmodes.animation.data.VEEditAnimationDataPosition
import good.damn.editor.editmodes.listeners.VEIListenerOnChangeEntityAnimation
import good.damn.editor.editmodes.listeners.VEIListenerOnChangeValueAnimation
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.interfaces.VEIColorPickable
import good.damn.editor.views.VEViewAnimatorEditor

class VEFragmentVectorAnimation
: Fragment(),
VEIColorPickable,
VEIListenerOnChangeEntityAnimation,
VEIListenerOnChangeValueAnimation {

    var onClickBtnPrev: View.OnClickListener? = null
    var onPlayAnimation: (() -> Array<VEOptionAnimatorBase>?)? = null

    var onTickUpdateAnimation: (()->Unit)? = null

    private var mViewEditor: VEViewAnimatorEditor? = null

    override fun onChangeEntityAnimation(
        entity: VEEditAnimationEntity
    ) {
        mViewEditor?.apply {
            options = entity.options
            layoutEditor()
            invalidate()
        }
    }

    override fun onChangeValueAnimation(
        entity: VEEditAnimationEntity,
        value: Any
    ) = mViewEditor?.run {
        options?.forEach {
            (it as? VEOptionAnimatorPosition)?.apply {
                if (value !is VEEditAnimationDataPosition)
                    return@forEach
                tickTimer.tickX = value.x
                tickTimer.tickY = value.y
                return@forEach
            }

            (it as? VEOptionAnimatorColor)?.apply {
                if (value !is Int)
                    return@forEach

                tickTimer.color = value
                return@forEach
            }
        }

    } ?: Unit

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

        mViewEditor = VEViewAnimatorEditor(
            context,
            0.35f,
            0.2f,
            0.25f
        ).apply {
            setBackgroundColor(0)
            tickUpdate = onTickUpdateAnimation

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
                    mViewEditor?.apply {
                        options = onPlayAnimation?.invoke()
                        layoutEditor()
                        play()
                    }
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
                    mViewEditor?.pause()
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