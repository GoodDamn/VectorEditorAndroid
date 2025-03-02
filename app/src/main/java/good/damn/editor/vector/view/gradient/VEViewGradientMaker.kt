package good.damn.editor.vector.view.gradient

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.Log
import android.view.MotionEvent
import android.view.View

class VEViewGradientMaker(
    context: Context
): View(
    context
) {

    companion object {
        private const val TAG = "VEViewGradientMaker"
    }

    var colors: List<VECanvasColorSeek>? = null
        set(v) {
            field = v

            v?.apply {
                mColors = IntArray(size).apply {
                    for (i in indices) {
                        this[i] = v[i].color
                    }
                }
                mPositions = FloatArray(size)
                return
            }

            mColors = intArrayOf()
            mPositions = floatArrayOf()
        }

    private var mCurrentColorSeek: VECanvasColorSeek? = null
    private var mCurrentColorSeekIndex = 0

    private var mColors = intArrayOf()
    private var mPositions = floatArrayOf()

    private val mPaintSeek = Paint().apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private var mx = 0f
    private var my = 0f
    private var mxx = 0f

    override fun onLayout(
        changed: Boolean,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int
    ) {
        super.onLayout(
            changed,
            left, top,
            right, bottom
        )

        val w = width.toFloat()
        val h = height.toFloat()

        mx = w * 0.1f
        mxx = w * 0.9f

        my = h * 0.5f

        mPaintSeek.strokeWidth = h * 0.1f

        colors?.forEach {
            it.layout(
                0f,
                0f,
                w,
                h
            )
        }
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        canvas.drawLine(
            mx,
            my,
            mxx,
            my,
            mPaintSeek
        )

        colors?.forEach {
            it.draw(
                canvas
            )
        }
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {
        event ?: return false
        colors ?: return false


        mCurrentColorSeek?.apply {

            mPositions[
                mCurrentColorSeekIndex
            ] = event.x / width

            rectColor.set(
                event.x.toInt(),
                rectColor.top,
                (event.x + rectColor.width()).toInt(),
                rectColor.bottom
            )

            mPaintSeek.shader = LinearGradient(
                mx,
                my,
                mxx,
                my,
                mColors,
                mPositions,
                Shader.TileMode.CLAMP
            )

            when (event.action) {
                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {
                    mCurrentColorSeek = null
                    mCurrentColorSeekIndex = 0
                }
            }

            invalidate()

            return true
        }

        for (it in colors!!.indices) {
            if (colors!![it].touchEvent(event)) {
                mCurrentColorSeek = colors!![it]
                mCurrentColorSeekIndex = it
                return true
            }
        }

        return false
    }

}