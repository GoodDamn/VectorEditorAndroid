package good.damn.editor.vector.view.gradient

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import good.damn.editor.vector.extensions.containsX
import good.damn.editor.vector.view.gradient.interfaces.VEIListenerOnGradientColorSeek
import good.damn.editor.vector.view.gradient.interfaces.VEIListenerOnGradientShader

class VEViewGradientMaker(
    context: Context
): View(
    context
) {

    companion object {
        private const val TAG = "VEViewGradientMaker"
    }

    var onGradientShader: VEIListenerOnGradientShader? = null
    var onSelectColor: VEIListenerOnGradientColorSeek? = null

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
                invalidateGradient()
                return
            }

            mColors = intArrayOf()
            mPositions = floatArrayOf()
        }

    inline fun layoutColorSeekById(
        v: Int
    ) {
        colors?.getOrNull(v)?.apply {
            layout(
                0f,
                0f,
                width.toFloat(),
                height.toFloat()
            )
        }
    }

    private var mCurrentColorSeek: VECanvasColorSeek? = null
    private var mCurrentColorSeekIndex = 0
    private var mCurrentColorSeekDtX = 0f

    private var mColors = intArrayOf()
    private var mPositions = floatArrayOf()

    private val mPaintSeek = Paint().apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
    }

    private val mPaintTriangle = Paint().apply {
        style = Paint.Style.STROKE
        color = 0xffffffff.toInt()
    }

    private var mx = 0f
    private var my = 0f
    private var mxx = 0f

    private var mSeekWidth = 0f

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

        mSeekWidth = mxx - mx

        my = h * 0.5f

        mPaintSeek.strokeWidth = h * 0.1f

        mPaintTriangle.strokeWidth = w * 0.01f

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

            val a = it.rectColor.centerX().toFloat()
            canvas.drawLine(
                a,
                it.rectColor.bottom.toFloat(),
                a,
                my,
                mPaintTriangle
            )
        }
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {
        event ?: return false

        mCurrentColorSeek?.apply {

            val px = rectColor.centerX()

            val l = mx.toInt() - (px - rectColor.left)
            val left = if (event.x < l)
                l
            else (event.x - mCurrentColorSeekDtX).toInt()

            rectColor.set(
                left,
                rectColor.top,
                left + rectColor.width(),
                rectColor.bottom
            )

            mPositions[
                mCurrentColorSeekIndex
            ] = (rectColor.centerX()-mx) / mSeekWidth

            invalidateGradient()

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

        colors?.apply {
            for (it in indices) {
                val color = this[it]

                if (color.touchEvent(event)) {
                    onSelectColor?.onSelectColorSeek(
                        it
                    )
                    return false
                }

                if (color.rectColor.containsX(
                    event.x.toInt()
                )) {
                    mCurrentColorSeek = color
                    mCurrentColorSeekIndex = it
                    mCurrentColorSeekDtX = event.x - color.rectColor.left
                    return true
                }
            }
        }

        return false
    }

    fun updateGradientColorSeek(
        colorIndex: Int,
        @ColorInt color: Int
    ) {
        mColors[colorIndex] = color

        colors?.getOrNull(
            colorIndex
        )?.color = color

        invalidateGradient()
    }

    private inline fun invalidateGradient() {
        LinearGradient(
            mx,
            my,
            mxx,
            my,
            mColors,
            mPositions,
            Shader.TileMode.CLAMP
        ).apply {
            mPaintSeek.shader = this
            onGradientShader?.onGetGradientShader(
                mColors,
                mPositions
            )
        }
    }

}