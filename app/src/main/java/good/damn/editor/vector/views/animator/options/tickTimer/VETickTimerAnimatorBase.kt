 package good.damn.editor.vector.views.animator.options.tickTimer

import android.graphics.Canvas
import android.graphics.RectF
import android.util.Log
import good.damn.editor.vector.structures.BinaryTree
import java.util.LinkedList

 abstract class VETickTimerAnimatorBase
: VETickTimerAnimator {

    companion object {
        private val TAG = VETickTimerAnimatorBase::class.simpleName
    }

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

    var width: Float
        get() = mRect.width()
        set(v) {
            mRect.right = mRect.left + v
        }

    var height: Float
        get() = mRect.height()
        set(v) {
            mRect.bottom = mRect.top + v
        }

    protected val mRect = RectF()

     val tickList = BinaryTree<Float>(
         equality = { v, v2 ->
             v == v2
         },
         moreThan = { v, v2 ->
             v > v2
         }
     )

    override fun layoutGrid(
        width: Float,
        height: Float
    ) {
        this.width = width
        this.height = height
    }

    override fun tick(
        tickTimeMs: Int,
        tickTimeFactor: Float
    ) {
        tickList.add(
            tickTimeFactor
        )

        Log.d(TAG, "tick: begin forEach")
        tickList.forEach {
            Log.d(TAG, "tick: $it")
        }
    }

}