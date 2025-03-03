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
    private val gradientEdit: VEMGradientEdit,
    context: Context
): View(
    context
) {

    companion object {
        private const val TAG = "VEViewGradientMaker"
    }

    var onGradientShader: VEIListenerOnGradientShader? = null
    var onSelectColor: VEIListenerOnGradientColorSeek? = null

    private var mColorsSeek: List<VECanvasColorSeek>? = null

    private var mCurrentColorSeek: VECanvasColorSeek? = null
    private var mCurrentColorSeekIndex = 0
    private var mCurrentColorSeekDtX = 0f

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

        mColorsSeek?.forEachIndexed { i, it ->
            it.layout(
                0f,
                0f,
                w,
                h
            )

            val l = (
                mx + (mxx - mx) * gradientEdit.positions[i]
            ).toInt()
            val ww = it.rectColor.width()

            it.rectColor.left = l
            it.rectColor.right = l + ww
        }

        invalidateGradient()
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


        mColorsSeek?.forEach {
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

            gradientEdit.positions[
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

        mColorsSeek?.apply {
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

    fun setInitialValues(
        colorsSeek: List<VECanvasColorSeek>
    ) {
        mColorsSeek = colorsSeek
        invalidateGradient()
    }

    fun layoutColorSeekById(
        v: Int
    ) {
        mColorsSeek?.getOrNull(v)?.apply {
            layout(
                0f,
                0f,
                width.toFloat(),
                height.toFloat()
            )
        }
    }

    fun updateGradientColorSeek(
        colorIndex: Int,
        @ColorInt color: Int
    ) {
        gradientEdit.colors[colorIndex] = color

        mColorsSeek?.getOrNull(
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
            gradientEdit.colors,
            gradientEdit.positions,
            Shader.TileMode.CLAMP
        ).apply {
            mPaintSeek.shader = this
            onGradientShader?.onReadyGradientShader()
        }
    }

}