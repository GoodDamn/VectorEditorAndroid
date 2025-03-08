package good.damn.editor.editmodes

import android.util.Log
import android.view.MotionEvent
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectPoint
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectShape
import good.damn.editor.editmodes.listeners.VEIListenerOnTransform
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.interfaces.VEITouchable

class VEEditModeTransform
: VEITouchable {

    companion object {
        private const val TAG = "VEEditModeTransform"
    }

    var transformListener: VEIListenerOnTransform? = null

    private var mx2 = 0f
    private var my2 = 0f

    private var mx1 = 0f
    private var my1 = 0f

    private var mTranslateX = 0f
    private var mTranslateY = 0f

    private var mTranslate2X = 0f
    private var mTranslate2Y = 0f


    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        when (
            event.action
        ) {
            MotionEvent.ACTION_DOWN -> {
                mx1 = event.rawX
                my1 = event.rawY
            }

            MotionEvent.ACTION_MOVE -> {
                mTranslate2X = mTranslateX + event.rawX - mx1
                mTranslate2Y = mTranslateY + event.rawY - my1
                transformListener?.onTranslate(
                    mTranslate2X,
                    mTranslate2Y
                )
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                mTranslateX = mTranslate2X
                mTranslateY = mTranslate2Y
            }
        }

        return true
    }

}