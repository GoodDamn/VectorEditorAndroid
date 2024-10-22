package good.damn.editor.vector.views.animator.options

import android.graphics.RectF
import androidx.core.graphics.xor

abstract class VEOptionAnimatorBase
: VEOptionAnimator {

    var width = 0f
        private set

    var height = 0f
        private set

    var x: Float
        get() = mRect.left
        set(v) {
            mRect.left = v
        }

    var y: Float
        get() = mRect.top
        set(v) {
            mRect.top = v
        }

    protected val mRect = RectF()

    override fun layout(
        width: Float,
        height: Float
    ) {
        this.width = width
        this.height = height

        mRect.apply {
            right = left + width
            bottom = top + height
        }
    }

}