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
        set(v) {
            field = v
            mCurrentPrimitive?.isAlignedHorizontal = v
        }

    var isAlignedVertical = false
        set(v) {
            field = v
            mCurrentPrimitive?.isAlignedVertical = v
        }

    private var mIsDraggingVector = false

    @get:ColorInt
    @setparam:ColorInt
    var color: Int = 0
        set(v) {
            field = v
            mCurrentPrimitive?.color = v
        }

    var primitives = LinkedList<VEPaintBase>()
    private var mCurrentPrimitive: VEPaintBase? = null

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

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mIsDraggingVector = false

                for (i in primitives) {
                    if (i.onDragVector(
                       event.x,
                       event.y
                    )) {
                        mIsDraggingVector = true
                        i.isAlignedHorizontal = isAlignedHorizontal
                        i.isAlignedVertical = isAlignedVertical
                        mCurrentPrimitive = i
                        break
                    }
                }

                if (mIsDraggingVector) {
                    return true
                }

                mCurrentPrimitive = selectPrimitive(
                    currentPrimitive,
                    width.toFloat(),
                    height.toFloat()
                ).apply {
                    onDown(
                        event.x,
                        event.y
                    )
                    isAlignedVertical = this@VEViewVector.isAlignedVertical
                    isAlignedHorizontal = this@VEViewVector.isAlignedHorizontal
                    strokeWidth = this@VEViewVector.strokeWidth
                    color = this@VEViewVector.color
                }
                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                if (mIsDraggingVector) {
                    mCurrentPrimitive?.onDragMove(
                        event.x,
                        event.y
                    )
                } else {
                    mCurrentPrimitive?.onMove(
                        event.x,
                        event.y
                    )
                }
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                if (mIsDraggingVector) {
                    mCurrentPrimitive = null
                    return true
                }

                mCurrentPrimitive?.apply {
                    onUp(
                        event.x,
                        event.y
                    )

                    primitives.add(
                        this
                    )
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