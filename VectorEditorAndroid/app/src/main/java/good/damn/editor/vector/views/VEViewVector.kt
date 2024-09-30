package good.damn.editor.vector.views

import android.content.Context
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import good.damn.editor.vector.enums.VEEnumOptions
import good.damn.editor.vector.paints.VEPaintBase
import good.damn.editor.vector.skeleton.VESkeleton2D
import good.damn.editor.vector.skeleton.VESkeletonPoint
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

    var anchorOption = VEEnumOptions.MOVE

    var isSerialDraw = false

    var isAlignedHorizontal = false
    var isAlignedVertical = false

    private var mSkeletonPoint: VESkeletonPoint? = null
    private val mSkeleton2D = VESkeleton2D()

    @get:ColorInt
    @setparam:ColorInt
    var color: Int = 0
        set(v) {
            field = v
            currentPrimitive?.color = v
        }

    var primitives = LinkedList<VEPaintBase>()
    var currentPrimitive: VEPaintBase? = null

    private var mIsExistedVector = false

    private var moveX = 0f
    private var moveY = 0f

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        primitives.forEach {
            it.onDraw(canvas)
        }

        currentPrimitive?.apply {
            onDraw(canvas)
        }
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

                if (anchorOption == VEEnumOptions.HOOK) {
                    val srcPrimitive = currentPrimitive

                    currentPrimitive = primitives.find {
                        it.onCheckCollision(
                            moveX, moveY
                        )
                    }

                    if (srcPrimitive != null) {
                        val targetPrimitive = currentPrimitive
                            ?: return false

                        srcPrimitive.onAffect(
                            targetPrimitive
                        )

                        targetPrimitive.selectedPoint?.let {
                            mSkeleton2D.addSkeletonPoint(
                                it.x, it.y
                            )?.apply {
                                if (isEmpty()) {
                                    add(it)
                                }
                                val p = srcPrimitive.selectedPoint
                                    ?: return@apply
                                add(p)
                            }
                        }

                        invalidate()
                    }

                    return false
                }

                mIsExistedVector = false

                val foundPrimitive = primitives.find {
                    it.onCheckCollision(
                        tempX, tempY
                    )
                }

                if (foundPrimitive != null) {
                    mIsExistedVector = true
                    currentPrimitive = foundPrimitive
                    foundPrimitive.selectedPoint?.let {
                        mSkeletonPoint = mSkeleton2D.find(
                            it.x,
                            it.y
                        )
                        Log.d(TAG, "onTouchEvent: UP: SKELET: $mSkeletonPoint")
                        currentPrimitive = null
                    }
                    return true
                }

                currentPrimitive = currentPrimitive?.newInstance(
                    width.toFloat(),
                    height.toFloat()
                )?.apply {
                    onDown(moveX, moveY)
                    strokeWidth = this@VEViewVector.strokeWidth
                    color = this@VEViewVector.color
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

                mSkeletonPoint?.apply {
                    mSkeleton2D.changePosition(
                        this,
                        moveX,
                        moveY
                    )
                    invalidate()
                    return true
                }

                currentPrimitive?.onMove(
                    moveX,
                    moveY
                )
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                mSkeletonPoint = null
                currentPrimitive?.apply {
                    onUp(
                        moveX,
                        moveY
                    )

                    if (!mIsExistedVector) {
                        primitives.add(
                            this
                        )
                    }
                }
                invalidate()
            }
        }


        return true
    }

    fun undoVector() = primitives.run {
        currentPrimitive = null
        if (isEmpty()) {
            return@run
        }
        primitives.removeLast()
        invalidate()
    }

    fun clearVector() {
        currentPrimitive = null
        primitives.clear()
        invalidate()
    }
}