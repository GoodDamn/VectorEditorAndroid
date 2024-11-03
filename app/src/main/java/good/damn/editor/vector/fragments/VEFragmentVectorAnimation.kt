package good.damn.editor.vector.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import good.damn.editor.animation.VEPointIndexedAnimation
import good.damn.editor.animation.animator.options.VEOptionAnimatorBase
import good.damn.editor.animation.animator.options.VEOptionAnimatorColor
import good.damn.editor.animation.animator.options.VEOptionAnimatorPosition
import good.damn.editor.animation.animator.options.tickTimer.listeners.VEListenerOnTickColor
import good.damn.editor.editmodes.listeners.VEIListenerOnChangePoint
import good.damn.editor.editmodes.listeners.VEIListenerOnChangePointPosition
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.interfaces.VEIColorPickable
import good.damn.editor.views.VEViewAnimatorEditor
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase

class VEFragmentVectorAnimation
: Fragment(),
VEIColorPickable,
VEIListenerOnChangePoint,
VEIListenerOnChangePointPosition {

    var onClickBtnPrev: View.OnClickListener? = null
    var onPlayAnimation: (() -> Array<VEOptionAnimatorBase>?)? = null

    private var mViewEditor: VEViewAnimatorEditor? = null

    override fun onChangePoint(
        point: VEPointIndexedAnimation
    ) {
        mViewEditor?.apply {
            options = point.options
            layoutEditor()
            invalidate()
        }
    }

    override fun onChangePointPosition(
        x: Float,
        y: Float
    ) {
        mViewEditor?.apply {
            (options?.firstOrNull() as? VEOptionAnimatorPosition)?.apply {
                tickTimer.tickX = x
                tickTimer.tickY = y
            }
        }
    }

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