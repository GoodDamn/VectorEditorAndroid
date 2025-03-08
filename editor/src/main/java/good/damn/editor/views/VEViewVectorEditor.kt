package good.damn.editor.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VEITouchable

class VEViewVectorEditor(
    context: Context,
    startMode: VEITouchable
): View(
    context
) {

    companion object {
        private const val TAG = "VEViewVector"
    }

    var mode: VEITouchable = startMode
    var canvasRenderer: VEIDrawable? = null

    var scale = 1.0f
    var translateX = 0f
    var translateY = 0f

    private val matrixView = Matrix()
    private val matrixInverted = Matrix()

    private var mx = 0f
    private var my = 0f

    private val mPaint = Paint().apply {
        strokeWidth = 9f
        style = Paint.Style.STROKE
        color = 0x19191919
    }

    fun updateTransformation() {
        matrixView.setScale(
            scale, scale,
            mx, my
        )

        matrixView.postTranslate(
            translateX,
            translateY
        )

        matrixInverted.set(
            matrixView
        )

        matrixInverted.invert(
            matrixInverted
        )
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

        mx = width / 2f
        my = height / 2f
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        canvas.concat(
            matrixView
        )

        canvas.drawLine(
            mx,
            0f,
            mx,
            height.toFloat(),
            mPaint
        )

        canvas.drawLine(
            0f,
            my,
            width.toFloat(),
            my,
            mPaint
        )

        canvasRenderer?.draw(
            canvas
        )
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {
        if (event == null) {
            return false
        }

        event.transform(
            matrixInverted
        )

        val b = mode.onTouchEvent(
            event
        )

        invalidate()

        return b
    }
}