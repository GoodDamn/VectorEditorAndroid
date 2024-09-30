package good.damn.editor.vector.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import good.damn.editor.vector.enums.VEEnumOptions
import good.damn.editor.vector.paints.VEPaintBase
import good.damn.editor.vector.skeleton.VESkeleton2D
import java.util.LinkedList

class VEViewVector(
    context: Context
): View(
    context
) {
    companion object {
        private const val TAG = "VEViewVector"
    }

    var strokeWidth = 0f
        set(v) {
            field = v
            currentPrimitive?.strokeWidth = v
        }

    @get:ColorInt
    @setparam:ColorInt
    var color: Int = 0
        set(v) {
            field = v
            currentPrimitive?.color = color
        }

    var anchorOption = VEEnumOptions.MOVE

    var isSerialDraw = false
    var isAlignedHorizontal = false
    var isAlignedVertical = false

    val primitives = LinkedList<
        VEPaintBase
    >()

    var currentPrimitive: VEPaintBase? = null

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

        currentPrimitive?.onDraw(
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

                if (!isSerialDraw) {
                    moveX = tempX
                    moveY = tempY
                }

                val prevPoint = mSelectedPoint

                mSelectedPoint = mSkeleton2D.find(
                    moveX,
                    moveY
                )

                if (mSelectedPoint == null) {
                    // New point
                    // New primitive
                    mSkeleton2D.addSkeletonPoint(
                        PointF(
                            moveX,
                            moveY
                        )
                    )

                    mSelectedPoint = mSkeleton2D
                        .getLastPoint()

                    if (prevPoint == null) {
                        invalidate()
                        return true
                    }
                    currentPrimitive?.newInstance(
                        width.toFloat(),
                        height.toFloat()
                    )?.apply {
                        currentPrimitive = this

                        color = this@VEViewVector.color
                        strokeWidth = this@VEViewVector.strokeWidth

                        points[0] = prevPoint
                        points[1] = mSelectedPoint

                        primitives.add(
                            this
                        )
                    }

                }

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