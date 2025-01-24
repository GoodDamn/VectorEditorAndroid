package good.damn.editor.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import good.damn.editor.animation.animator.options.canvas.VECanvasOption
import good.damn.editor.animation.animator.options.canvas.VEIAnimationCanvas
import good.damn.editor.animation.animator.options.canvas.VEIAnimationOptionCanvas
import good.damn.editor.animation.animator.options.canvas.VEIRequesterFloat
import good.damn.editor.animation.animator.options.canvas.VEITransactionReceiver
import good.damn.editor.animation.animator.scroller.VEScrollerHorizontal
import good.damn.editor.animation.animator.scroller.vertical.VEScrollerVertical
import good.damn.editor.animation.animator.ticker.VEAnimatorTicker
import good.damn.editor.animation.animators.VEAnimator
import good.damn.editor.animation.animators.VEAnimatorTick
import good.damn.sav.core.animation.keyframe.VEIAnimation
import good.damn.sav.misc.interfaces.VEITouchable
import kotlin.math.abs

class VEViewAnimatorEditor(
    context: Context?
): View(
    context
), VEIRequesterFloat {

    companion object {
        private val TAG = VEViewAnimatorEditor::class.simpleName
    }


    private var mOptionsDraw: Array<VECanvasOption>? = null

    var animation: VEIAnimationCanvas? = null
        set(v) {
            field = v
            v?.apply {
                mOptionsDraw = Array(
                    options.size
                ) {
                    val option = options[it]
                    VECanvasOption(
                        option.preview,
                        option.keyframe
                    )
                }

                layoutEditor()
            }
        }


    private val mAnimator = VEAnimator().apply {
        listener = VEAnimatorTick()
    }

    private val mTicker = VEAnimatorTicker()
    private val mScrollerVertical = VEScrollerVertical()

    private var mCurrentTouch: VEITouchable? = null

    private val mPaintText = Paint().apply {
        color = mTicker.color
    }

    fun pause() {
        //mAnimator.stop()
    }

    fun play(
        atTimeMs: Long = 0L
    ) {
        /*mAnimator.play(
            atTimeMs
        )*/
    }

    fun layoutEditor() {
        val heightTicker = height * 0.22f
        val widthPreview = width * 0.2f
        val widthOption = width.toFloat()

        var y = heightTicker
        mOptionsDraw?.forEach {
            it.layout(
                0f,
                y,
                widthOption,
                widthPreview,
                heightTicker
            )

            y += heightTicker
        }

        mTicker.layout(
            0f,
            heightTicker,
            widthPreview,
            widthOption
        )

        mScrollerVertical.apply {
            reset()
            triggerEndX = widthPreview * 0.5f
        }

        mPaintText.textSize = heightTicker * 0.25f
    }

    override fun requestDataFloat() = mTicker.tickPosition

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

        layoutEditor()
    }

    override fun onDraw(
        canvas: Canvas
    ) = canvas.run {

        super.onDraw(
            canvas
        )

        save()

        translate(
            0f,
            mScrollerVertical.scrollValue
        )

        mOptionsDraw?.forEach {
            it.draw(
                canvas
            )
        }

        restore()

        mTicker.draw(
            this
        )
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {
        if (event == null) {
            return false
        }

        mCurrentTouch?.let {
            if (!it.onTouchEvent(event)) {
                mCurrentTouch = null
            }
            invalidate()
            return true
        }

        if (mTicker.onTouchEvent(
            event
        )) {
            mCurrentTouch = mTicker
            invalidate()
            return true
        }

        if (mScrollerVertical.onTouchEvent(
            event
        )) {
            mCurrentTouch = mScrollerVertical
            invalidate()
            return true
        }

        mOptionsDraw?.forEach {
            if (it.onTouchEvent(event)) {
                mCurrentTouch = it
                invalidate()
                return true
            }
        }

        return false
    }

}