package good.damn.editor.vector.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent
import android.view.View
import good.damn.editor.vector.enums.VEEnumPrimitives
import good.damn.editor.vector.models.view.VEModelLine
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

    private val mPrimitives = LinkedList<VEPaintBase>()
    private var mCurrentPrimitive: VEPaintBase? = null

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        mPrimitives.forEach {
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
                    currentPrimitive
                ).apply {
                    onDown(
                        event.x,
                        event.y
                    )
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

                    mPrimitives.add(
                        this
                    )
                }
                invalidate()
            }
        }


        return true
    }
}

private fun VEViewVector.selectPrimitive(
    primitive: VEEnumPrimitives
) = when (primitive) {
    VEEnumPrimitives.CIRCLE -> VEPaintCircle()
    else -> VEPaintLine()
}