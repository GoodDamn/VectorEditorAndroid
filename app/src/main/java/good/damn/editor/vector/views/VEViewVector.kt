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

    @get:ColorInt
    @setparam:ColorInt
    var color: Int = 0xffff0000.toInt()
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

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(
            changed,
            left,
            top,
            right,
            bottom
        )
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {
        if (event == null) {
            return false
        }

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mCurrentPrimitive = selectPrimitive(
                    currentPrimitive,
                    width.toFloat(),
                    height.toFloat()
                ).apply {
                    onDown(
                        event.x,
                        event.y
                    )
                    strokeWidth = this@VEViewVector.strokeWidth
                    color = this@VEViewVector.color
                }
                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                mCurrentPrimitive?.onMove(
                    event.x,
                    event.y
                )
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
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