package good.damn.editor.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import good.damn.editor.animation.animator.options.canvas.VEIAnimationOptionCanvas
import good.damn.editor.transaction.VEIRequesterFloat
import good.damn.editor.animation.animator.scroller.vertical.VEScrollerVertical
import good.damn.editor.animation.animator.ticker.VEAnimatorTicker
import good.damn.sav.core.animation.animators.VEAnimatorGlobal
import good.damn.sav.core.animation.animators.VEIListenerAnimation
import good.damn.sav.core.animation.animators.VEIListenerAnimationUpdateFrame
import good.damn.sav.misc.interfaces.VEITouchable

class VEViewAnimatorEditor(
    context: Context?
): View(
    context
), VEIRequesterFloat {

    companion object {
        private val TAG = VEViewAnimatorEditor::class.simpleName
    }

    private val mAnimator = VEAnimatorGlobal()

    private val mTicker = VEAnimatorTicker()
    private val mScrollerVertical = VEScrollerVertical()

    private var mCurrentTouch: VEITouchable? = null

    private val mPaintText = Paint().apply {
        color = mTicker.color
    }

    var onUpdateFrameAnimation: VEIListenerAnimationUpdateFrame?
        get() = mAnimator.onUpdateFrameAnimation
        set(v) {
            mAnimator.onUpdateFrameAnimation = v
        }

    var animations: List<VEIAnimationOptionCanvas>? = null
        set(v) {
            field = v
            layoutEditor()
        }

    fun pause() {
        mAnimator.stop()
    }

    fun play(
        atTimeMs: Long = 0L
    ) = animations?.run {
        mAnimator.play(
            atTimeMs,
            ArrayList<VEIListenerAnimation>().apply {
                var anim: VEIListenerAnimation?
                for (i in this@run.indices) {
                    anim = this@run[i].createAnimator()
                    if (anim == null) {
                        continue
                    }

                    add(anim)
                }

            }
        )
    }

    fun layoutEditor() {
        val heightTicker = height * 0.22f
        val widthOption = width.toFloat()
        val widthPreview = widthOption * 0.2f
        val widthKeyframe = widthOption - widthPreview

        var y = heightTicker
        animations?.forEach {
            it.keyframe.layout(
                widthPreview,
                y,
                widthKeyframe,
                heightTicker
            )

            it.preview.layout(
                0f,
                y,
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

        animations?.forEach {
            it.keyframe.draw(canvas)
            it.preview.draw(canvas)
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

        animations?.forEach {
            if (it.preview.onTouchEvent(event)) {
                mCurrentTouch = it.preview
                invalidate()
                return true
            }

            if (it.keyframe.onTouchEvent(event)) {
                mCurrentTouch = it.keyframe
                invalidate()
                return true
            }
        }

        return false
    }
}