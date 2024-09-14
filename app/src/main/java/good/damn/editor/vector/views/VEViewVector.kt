package good.damn.editor.vector.views

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import good.damn.editor.vector.enums.VEEnumPrimitives
import good.damn.editor.vector.paints.VEPaintBase
import good.damn.editor.vector.paints.VEPaintCircle
import good.damn.editor.vector.paints.VEPaintLine
import java.util.LinkedList

class VEViewVector(
    context: Context
): View(
    context
) {

    companion object {
        private const val TAG = "VEViewVector"
    }

    var currentPrimitive = VEEnumPrimitives.LINE

    var strokeWidth = 0f
        set(v) {
            field = v
            mCurrentPrimitive?.strokeWidth = v
        }

    var isAlignedHorizontal = false
    var isAlignedVertical = false

    @get:ColorInt
    @setparam:ColorInt
    var color: Int = 0
        set(v) {
            field = v
            mCurrentPrimitive?.color = v
        }

    var primitives = LinkedList<VEPaintBase>()
    private var mCurrentPrimitive: VEPaintBase? = null
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

        mCurrentPrimitive?.apply {
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
                mIsExistedVector = false

                moveX = event.x
                moveY = event.y

                mCurrentPrimitive = primitives.find {
                    it.onCheckCollision(
                        moveX, moveY
                    )
                }

                if (mCurrentPrimitive != null) {
                    mIsExistedVector = true
                    return true
                }

                mCurrentPrimitive = selectPrimitive(
                    currentPrimitive,
                    width.toFloat(),
                    height.toFloat()
                ).apply {
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

                mCurrentPrimitive?.onMove(
                    moveX,
                    moveY
                )
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                mCurrentPrimitive?.apply {
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
                mCurrentPrimitive = null
                invalidate()
            }
        }


        return true
    }

    fun undoVector() = primitives.run {
        mCurrentPrimitive = null
        if (isEmpty()) {
            return@run
        }
        primitives.removeLast()
        invalidate()
    }

    fun clearVector() {
        mCurrentPrimitive = null
        primitives.clear()
        invalidate()
    }
}

private fun VEViewVector.selectPrimitive(
    primitive: VEEnumPrimitives,
    canvasWidth: Float,
    canvasHeight: Float
) = when (primitive) {
    VEEnumPrimitives.CIRCLE -> VEPaintCircle(
        canvasWidth,
        canvasHeight
    )
    else -> VEPaintLine(
        canvasWidth,
        canvasHeight
    )
}