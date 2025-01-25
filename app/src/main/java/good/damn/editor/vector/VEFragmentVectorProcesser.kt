package good.damn.editor.vector

import good.damn.editor.animation.animator.options.canvas.VEIAnimationOptionCanvas
import good.damn.editor.animation.animator.options.canvas.VEAnimationOptionCanvasColor
import good.damn.editor.animation.animator.options.canvas.VEAnimationOptionCanvasPosition
import good.damn.editor.animation.animator.options.canvas.VEAnimationOptionCanvasWidth
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectPoint
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectShape
import good.damn.editor.views.VEViewAnimatorEditor
import good.damn.sav.core.animation.keyframe.VEMAnimationOptionColor
import good.damn.sav.core.animation.keyframe.VEMAnimationOptionPosition
import good.damn.sav.core.animation.keyframe.VEMAnimationOptionWidth
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.misc.structures.tree.BinaryTree

class VEFragmentVectorProcesser
: VEIListenerOnSelectShape,
VEIListenerOnSelectPoint {

    private var mAnimations = HashMap<
        Int,
        List<VEIAnimationOptionCanvas>
    >(10)

    private var mCurrentAnimation: List<VEIAnimationOptionCanvas>? = null

    private var mCurrentAnimationId = -1

    var viewAnimatorEditor: VEViewAnimatorEditor? = null

    fun play() = viewAnimatorEditor?.run {
        animations = mAnimations.flatMap { it.value }
        invalidate()
        play()
    }

    fun pause() = viewAnimatorEditor?.pause()

    override fun onSelectShape(
        shape: VEShapeBase
    ) = selectAnimation(
        shape.hashCode()
    ) {
        arrayListOf(
            VEAnimationOptionCanvasColor(
                shape,
                VEMAnimationOptionColor(
                    BinaryTree(
                        equality = {v, vv -> v.factor == vv.factor},
                        moreThan = {v, vv -> v.factor > vv.factor}
                    ),
                    duration = 1000
                ),
                it
            ),
            VEAnimationOptionCanvasWidth(
                shape,
                VEMAnimationOptionWidth(
                    BinaryTree(
                        equality = {v, vv -> v.factor == vv.factor},
                        moreThan = {v, vv -> v.factor > vv.factor}
                    ),
                    duration = 1500
                ),
                it
            )
        )
    }

    override fun onSelectPoint(
        point: VEPointIndexed
    ) = selectAnimation(
        point.index
    ) {
        arrayListOf(
            VEAnimationOptionCanvasPosition(
                point,
                VEMAnimationOptionPosition(
                    BinaryTree(
                        equality = {v, vv -> v.factor == vv.factor},
                        moreThan = {v, vv -> v.factor > vv.factor}
                    ),
                    duration = 2000
                ),
                it
            )
        )
    }

    private inline fun selectAnimation(
        id: Int,
        create: (VEViewAnimatorEditor) -> ArrayList<VEIAnimationOptionCanvas>
    ) = viewAnimatorEditor?.run {
        if (mCurrentAnimationId == id) {
            return@run
        }

        mCurrentAnimationId = id
        mCurrentAnimation = mAnimations[id]

        if (mCurrentAnimation == null) {
            mCurrentAnimation = create(this)

            mAnimations[id] = mCurrentAnimation!!
        }

        animations = mCurrentAnimation

        invalidate()
    } ?: Unit

}