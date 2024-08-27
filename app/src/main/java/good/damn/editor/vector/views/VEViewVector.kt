package good.damn.editor.vector.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent
import android.view.View
import good.damn.editor.vector.models.view.VEModelLine
import java.util.LinkedList

class VEViewVector(
    context: Context
): View(
    context
) {

    companion object {
        private const val TAG = "VEViewVector"
    }

    private val mPaintLine = Paint().apply {
        color = 0xffff0000.toInt()
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        strokeWidth = resources
            .displayMetrics
            .widthPixels * 0.05f
    }

    private val mLines = LinkedList<VEModelLine>()
    private var mCurrentLine: VEModelLine? = null

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )
        mPaintLine.color = 0xffff0000.toInt()
        mLines.forEach {
            canvas.drawLine(
                it.p0.x,
                it.p0.y,
                it.p1.x,
                it.p1.y,
                mPaintLine
            )
        }

        mPaintLine.color = 0xffffff00.toInt()
        mCurrentLine?.apply {
            canvas.drawLine(
                p0.x,
                p0.y,
                p1.x,
                p1.y,
                mPaintLine
            )
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
                mCurrentLine = VEModelLine(
                    PointF(event.x,event.y),
                    PointF(event.x,event.y)
                )
                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                mCurrentLine?.apply {
                    p1.x = event.x
                    p1.y = event.y
                }
                invalidate()
            }

            MotionEvent.ACTION_UP -> {
                mCurrentLine?.let {
                    mLines.add(
                        it
                    )
                }
                invalidate()
            }
        }


        return true
    }

}