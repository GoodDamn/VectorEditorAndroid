package good.damn.editor.editmodes

import android.view.MotionEvent
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectPoint
import good.damn.sav.core.shapes.VEShapeFill
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.interfaces.VEITouchable

class VEEditModeFillPoints(
    private val skeleton: VESkeleton2D
): VEITouchable {

    var onSelectPoint: VEIListenerOnSelectPoint? = null

    private var mCurrentShape: VEShapeFill? = null

    fun createShape(
        width: Float,
        height: Float,
    ): VEShapeFill {
        val shape = VEShapeFill(
            width,
            height
        ).apply {
            color = 0xffff0000.toInt()
        }

        mCurrentShape = shape

        return shape
    }

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val point = skeleton.find(
                    event.x,
                    event.y
                ) ?: return false

                mCurrentShape?.points?.add(
                    point
                )

                onSelectPoint?.onSelectPoint(
                    point
                )

                return false
            }
        }

        return true
    }

}