package good.damn.editor.editmodes

import android.graphics.Matrix
import android.util.Log
import android.view.MotionEvent
import good.damn.editor.editmodes.listeners.VEIListenerOnTransform
import good.damn.sav.misc.Size
import good.damn.sav.misc.interfaces.VEITouchable
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

class VEEditModeTransform
: VEEditMode {

    companion object {
        private const val TAG = "VEEditModeTransform"
        private const val SCALE_FACTOR = 0.0025f
    }

    var transformListener: VEIListenerOnTransform? = null

    private var mPivotX = 0f
    private var mPivotY = 0f

    private var mPrevDistance = 0f
    private var mCurrentDistance = 0f

    private var mTranslateX = 0f
    private var mTranslateY = 0f

    private var mTranslate2X = 0f
    private var mTranslate2Y = 0f

    private var mScale = 1.0f

    override fun onTouchEvent(
        event: MotionEvent,
        invertedMatrix: Matrix
    ): Boolean {
        when (
            event.actionMasked
        ) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(TAG, "onTouchEvent: ACTION_DOWN")
                mPivotX = event.x
                mPivotY = event.y
            }
            
            MotionEvent.ACTION_POINTER_DOWN -> {
                Log.d(TAG, "onTouchEvent: ACTION_POINTER_DOWN ${event.pointerCount}")
                if (event.pointerCount == 2) {
                    event.apply {
                        mPrevDistance = hypot(
                            getX(1) - getX(0),
                            getY(1) - getY(0)
                        )

                    }
                }
            }

            MotionEvent.ACTION_MOVE -> {
                when {
                    event.pointerCount == 1 -> {
                        mTranslate2X = mTranslateX + event.x - mPivotX
                        mTranslate2Y = mTranslateY + event.y - mPivotY
                        transformListener?.onTranslate(
                            mTranslate2X,
                            mTranslate2Y
                        )
                    }

                    event.pointerCount > 1 -> {
                        val x = event.getX(0)
                        val y = event.getY(0)

                        val xx = event.getX(1)
                        val yy = event.getY(1)

                        mCurrentDistance = hypot(
                            xx - x,
                            yy - y
                        )

                        mScale += (mCurrentDistance - mPrevDistance) * SCALE_FACTOR
                        if (mScale > 7f) {
                            mScale = 7f
                        }
                        if (mScale < 0.4f) {
                            mScale = 0.4f
                        }
                        transformListener?.onScale(
                            mScale
                        )

                        mPrevDistance = mCurrentDistance
                    }
                }
            }

            MotionEvent.ACTION_POINTER_UP -> {
                if (event.pointerCount == 2) {
                    val pivotIndex = if (
                        event.actionIndex == 0
                    ) 1 else 0

                    mPivotX = event.getX(pivotIndex)
                    mPivotY = event.getY(pivotIndex)
                    mTranslateX = mTranslate2X
                    mTranslateY = mTranslate2Y
                }
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                Log.d(TAG, "onTouchEvent: ACTION_UP ${event.pointerCount}")
                mTranslateX = mTranslate2X
                mTranslateY = mTranslate2Y
            }
        }

        return true
    }

}