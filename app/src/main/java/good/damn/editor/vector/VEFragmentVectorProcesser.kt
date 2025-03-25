package good.damn.editor.vector

import good.damn.editor.animation.animator.options.canvas.VEIAnimationOptionCanvas
import good.damn.editor.animation.animator.options.canvas.VEAnimationOptionCanvasFill
import good.damn.editor.animation.animator.options.canvas.VEAnimationOptionCanvasPosition
import good.damn.editor.animation.animator.options.canvas.VEAnimationOptionCanvasWidth
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectFill
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectPoint
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectShape
import good.damn.editor.views.VEViewAnimatorEditor
import good.damn.sav.core.animation.interpolators.fill.VEFillGroupObserver
import good.damn.sav.core.animation.keyframe.VEKeyframes
import good.damn.sav.core.animation.keyframe.VEMAnimationOptionFill
import good.damn.sav.core.animation.keyframe.VEMAnimationOptionPosition
import good.damn.sav.core.animation.keyframe.VEMAnimationOptionWidth
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase

class VEFragmentVectorProcesser
: VEIListenerOnSelectShape,
VEIListenerOnSelectPoint,
VEIListenerOnSelectFill {

    private var mAnimations = HashMap<
        Int,
        MutableList<VEIAnimationOptionCanvas>
    >(10)

    private var mCurrentAnimation: MutableList<VEIAnimationOptionCanvas>? = null

    private var mCurrentAnimationId = 0xffff

    var duration = 2000 //ms

    var viewAnimatorEditor: VEViewAnimatorEditor? = null

    fun exportAnimations() = mAnimations.flatMap {
        it.value.mapNotNull {
            anim -> anim.exportAnimation()
        }
    }

    fun clearAnimations() {
        mAnimations.clear()
    }

    fun play() = viewAnimatorEditor?.run {
        animations = mAnimations.flatMap { it.value }
        invalidate()
        play()
    }

    fun pause() = viewAnimatorEditor?.pause()

    fun addAnimation(
        id: Int,
        animation: VEIAnimationOptionCanvas
    ) {
        mAnimations[id]?.apply {
            add(animation)
            return@apply
        }

        mAnimations[id] = arrayListOf(
            animation
        )
    }

    override fun onSelectFill(
        fill: VEFillGroupObserver
    ) = selectAnimation(
        fill.id.id
    ) {
        arrayListOf(
            VEAnimationOptionCanvasFill(
                fill,
                VEMAnimationOptionFill(
                    VEKeyframes(),
                    duration = 1000
                ),
                it
            )
        )
    }

    override fun onSelectShape(
        shape: VEShapeBase
    ) = selectAnimation(
        shape.hashCode()
    ) {
        arrayListOf(
            VEAnimationOptionCanvasWidth(
                shape,
                VEMAnimationOptionWidth(
                    VEKeyframes(),
                    duration = 1500
                ),
                it
            )
        )
    }

    override fun onSelectPoint(
        point: VEPointIndexed
    ) = selectAnimation(
        point.id.id
    ) {
        arrayListOf(
            VEAnimationOptionCanvasPosition(
                point,
                VEMAnimationOptionPosition(
                    VEKeyframes(),
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