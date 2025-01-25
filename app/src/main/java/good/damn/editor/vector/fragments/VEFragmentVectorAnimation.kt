package good.damn.editor.vector.fragments

import android.graphics.PointF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import good.damn.editor.animation.animator.options.canvas.VEIAnimationCanvas
import good.damn.editor.animation.animator.options.canvas.VEMAnimationCanvas
import good.damn.editor.animation.animator.options.canvas.VEMAnimationOptionCanvasPosition
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectPoint
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.interfaces.VEIColorPickable
import good.damn.editor.views.VEViewAnimatorEditor
import good.damn.sav.core.animation.animators.VEIListenerAnimationUpdateFrame
import good.damn.sav.core.animation.keyframe.VEMAnimationOptionPosition
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.misc.structures.tree.BinaryTree

class VEFragmentVectorAnimation
: Fragment(),
VEIColorPickable,
VEIListenerOnSelectPoint {

    var onClickBtnPrev: View.OnClickListener? = null

    var onUpdateFrameAnimation: VEIListenerAnimationUpdateFrame? = null

    private var mAnimations = HashMap<
        Int,
        VEIAnimationCanvas
    >(10)

    private var mCurrentAnimation: VEIAnimationCanvas? = null
    private var mCurrentAnimationPoint: VEPointIndexed? = null

    private var mViewEditor: VEViewAnimatorEditor? = null

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
            context
        ).apply {
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
                    mViewEditor?.apply {
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

    override fun onSelectPoint(
        point: VEPointIndexed
    ) = mViewEditor?.run {

        mCurrentAnimationPoint?.apply {
            if (index == point.index) {
                return@run
            }
        }

        mCurrentAnimationPoint = point

        mCurrentAnimation = mAnimations[
            point.index
        ]

        if (mCurrentAnimation == null) {
            mCurrentAnimation = VEMAnimationCanvas(
                arrayListOf(
                    VEMAnimationOptionCanvasPosition(
                        point,
                        VEMAnimationOptionPosition(
                            BinaryTree(
                                equality = {v, vv -> v.factor == vv.factor},
                                moreThan = {v, vv -> v.factor > vv.factor}
                            ),
                            duration = 2000
                        ),
                        this
                    )
                )
            )
            mAnimations[point.index] = mCurrentAnimation!!
        }

        animation = mCurrentAnimation
        invalidate()
    } ?: Unit

}