package good.damn.editor.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import good.damn.editor.animation.animator.VEButtonKeyFrame
import good.damn.editor.animation.animator.options.canvas.VECanvasOption
import good.damn.editor.animation.animator.options.canvas.VEITransactionReceiver
import good.damn.editor.animation.animator.options.canvas.VETransactionKeyFrame
import good.damn.editor.animation.animator.options.canvas.keyframes.VECanvasOptionKeyFramePosition
import good.damn.editor.animation.animator.options.canvas.previews.VECanvasOptionPreviewPosition
import good.damn.editor.animation.animator.scroller.VEScrollerHorizontal
import good.damn.editor.animation.animator.scroller.vertical.VEScrollerVertical
import good.damn.editor.animation.animator.ticker.VEAnimatorTicker
import good.damn.editor.animation.animators.VEAnimator
import good.damn.editor.animation.animators.VEAnimatorTick
import good.damn.sav.core.animation.keyframe.VEMAnimationOption
import good.damn.sav.core.animation.keyframe.VEMKeyFrame
import good.damn.sav.core.animation.keyframe.VEMKeyFrameDataPosition
import good.damn.sav.misc.interfaces.VEITouchable
import good.damn.sav.misc.structures.tree.BinaryTree
import java.util.LinkedList
import kotlin.math.abs

class VEViewAnimatorEditor(
    context: Context?
): View(
    context
), VEITransactionReceiver {

    companion object {
        private val TAG = VEViewAnimatorEditor::class.simpleName
    }

    private val mOptionPosition = VEMAnimationOption(
        BinaryTree(
            equality = {v, vv -> v.factor == vv.factor},
            moreThan = {v, vv -> v.factor > vv.factor}
        )
    )

    private val mCanvasKeyFramePosition = VECanvasOptionKeyFramePosition(
        mOptionPosition
    )

    private val mTransactionKeyFrame = VETransactionKeyFrame(
        this@VEViewAnimatorEditor
    )

    private var mOptionsDraw = Array(1) {
        VECanvasOption(
            VECanvasOptionPreviewPosition(
                mTransactionKeyFrame
            ),
            mCanvasKeyFramePosition
        )
    }

    var duration: Int = 1000 // ms
        set(v) {
            field = v

            mTimeDividers.clear()

            durationPx = (
                v / 1000f * (width - mScrollerHorizontal.triggerEndX)
            ).toInt()

            val fDuration = duration.toFloat()
            for (i in 0..duration step 1000) {
                mTimeDividers.add(
                    TimeDivider(
                        i / fDuration * durationPx,
                        height.toFloat(),
                        i.toString()
                    )
                )
            }

            mAnimator.duration = duration
        }

    var durationPx: Int
        get () = mOptionPosition.duration
        private set(v) {
            mOptionPosition.duration = v
        }

    private val mAnimator = VEAnimator().apply {
        listener = VEAnimatorTick()
    }

    private val mTicker = VEAnimatorTicker()
    private val mScrollerHorizontal = VEScrollerHorizontal()
    private val mScrollerVertical = VEScrollerVertical()
    private val mTimeDividers = LinkedList<TimeDivider>()

    private var mCurrentTouch: VEITouchable? = null

    private val mPaintText = Paint().apply {
        color = mTicker.color
    }

    fun pause() {
        mAnimator.stop()
    }

    fun play(
        atTimeMs: Long = 0L
    ) {
        mAnimator.play(
            atTimeMs
        )
    }

    fun layoutEditor() {
        val heightTicker = height * 0.16f
        val widthPreview = width * 0.15f
        val widthOption = width.toFloat()

        var y = heightTicker
        mOptionsDraw.forEach {
            it.layout(
                0f,
                y,
                widthOption,
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

        mScrollerHorizontal.apply {
            reset()
            triggerEndY = heightTicker
            triggerEndX = widthPreview
        }

        mScrollerVertical.apply {
            reset()
            triggerEndX = widthPreview * 0.5f
        }

        mPaintText.textSize = heightTicker * 0.25f

        duration = 2000
    }

    override fun onReceiveTransaction() {
        val factor = (abs(mScrollerHorizontal.scrollValue)
            + mTicker.tickPosition
        ) / durationPx
        mOptionPosition.keyFrames.add(
            VEMKeyFrame(
                factor,
                VEMKeyFrameDataPosition(
                    50f,
                    50f,
                )
            )
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

        mOptionsDraw.forEach {
            it.draw(
                canvas
            )
            it.scrollX = mScrollerHorizontal.scrollValue
        }

        for (it in mTimeDividers) {
            drawText(
                it.time,
                mScrollerHorizontal.triggerEndX
                        + it.scrollPosition
                        + mScrollerHorizontal.scrollValue,
                it.y,
                mPaintText
            )
        }

        val scrollDuration = ((abs(
            mScrollerHorizontal.scrollValue
        ) + mTicker.tickPosition) / durationPx * duration).toInt()

        drawText(
            scrollDuration.toString(),
            mTicker.tickPosition + mScrollerHorizontal.triggerEndX,
            height.toFloat(),
            mPaintText
        )

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

        if (mScrollerHorizontal.onTouchEvent(
            event
        )) {
            mCurrentTouch = mScrollerHorizontal
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

        mOptionsDraw.forEach {
            if (it.onTouchEvent(event)) {
                mCurrentTouch = it
                invalidate()
                return true
            }
        }

        return false
    }

}

private data class TimeDivider(
    val scrollPosition: Float,
    val y: Float,
    val time: String
)