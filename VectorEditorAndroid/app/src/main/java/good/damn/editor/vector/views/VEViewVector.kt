package good.damn.editor.vector.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import good.damn.editor.vector.interfaces.VEIOptionable
import good.damn.editor.vector.options.VEOptionPrimitivable
import good.damn.editor.vector.paints.VEPaintBase
import good.damn.editor.vector.skeleton.VESkeleton2D
import java.util.LinkedList

class VEViewVector(
    context: Context,
    startOption: VEIOptionable
): View(
    context
) {
    companion object {
        private const val TAG = "VEViewVector"
    }

    var optionable: VEIOptionable = startOption

    var isAlignedHorizontal = false
    var isAlignedVertical = false

    val primitives = LinkedList<
        VEPaintBase
    >()

    private val mSkeleton2D = VESkeleton2D()

    private var mSelectedPoint: PointF? = null

    private var moveX = 0f
    private var moveY = 0f

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        mSkeleton2D.onDraw(
            canvas
        )

        primitives.forEach {
            it.onDraw(
                canvas
            )
        }

        optionable.onDraw(
            canvas
        )
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {
        if (event == null) {
            return false
        }

        when (
            event.action
        ) {
            MotionEvent.ACTION_DOWN -> {
                val tempX = event.x
                val tempY = event.y

                mSelectedPoint = mSkeleton2D.find(
                    tempX,
                    tempY
                )

                if (mSelectedPoint == null) {
                    // New point
                    // New primitive
                    mSkeleton2D.addSkeletonPoint(
                        PointF(
                            tempX,
                            tempY
                        )
                    )

                    mSelectedPoint = mSkeleton2D
                        .getLastPoint()
                }

                optionable.runOption(
                    primitives,
                    mSelectedPoint
                )

                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                if (isAlignedHorizontal) {
                    moveX = event.x
                }

                if (isAlignedVertical) {
                    moveY = event.y
                }

                mSelectedPoint?.set(
                    moveX,
                    moveY
                )

                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                invalidate()
            }
        }


        return true
    }

    fun undoVector() {

    }

    fun clearVector() {
        invalidate()
    }
}