package good.damn.editor.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import good.damn.editor.animator.options.VEOptionAnimatorBase
import good.damn.editor.animator.scroller.VEScrollerHorizontal
import good.damn.editor.animator.scroller.vertical.VEScrollerVertical
import good.damn.editor.animator.ticker.VEAnimatorTicker
import good.damn.editor.animators.VEAnimatorVector
import good.damn.sav.misc.extensions.primitives.isInRange
import good.damn.sav.misc.interfaces.VEITouchable
import good.damn.sav.misc.scopes.TimeScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.LinkedList
import kotlin.math.abs

class VEViewAnimatorEditor(
    context: Context?,
    private val heightOptionFactor: Float,
    private val widthOptionFactor: Float,
    private val heightTickTriggerFactor: Float
): View(
    context
) {

    companion object {
        private val TAG = VEViewAnimatorEditor::class.simpleName
        var subInterpolation: Float = 0f
        var interpolation: Float = 0f
    }

    var options: Array<
        VEOptionAnimatorBase
    >? = null

    var duration: Int = 1000 // ms
        set(v) {
            field = v

            mTimeDividers.clear()

            durationPx = (
                v / 1000f * (width - mScrollerHorizontal.triggerEndX)
            ).toInt()

            options?.forEach {
                it.tickTimer.durationPx = durationPx
            }

            val fDuration = duration.toFloat()
            for (i in 0..duration step 1000) {
                mTimeDividers.add(
                    TimeDivider(
                        i / fDuration * durationPx,
                        i.toString()
                    )
                )
            }
        }

    var durationPx: Int = 0
        private set

    var isPlaying: Boolean = false
        private set

    private val mTicker = VEAnimatorTicker()
    private val mScrollerHorizontal = VEScrollerHorizontal()
    private val mScrollerVertical = VEScrollerVertical()
    private val mTimeDividers = LinkedList<TimeDivider>()

    private var mCurrentTouch: VEITouchable? = null

    private val mPaintText = Paint().apply {
        color = mTicker.color
    }

    private val mPaintDebugText = Paint().apply {
        color = 0xffffff00.toInt()
        textSize = 25f
    }

    private val mScope = TimeScope(
        Dispatchers.IO
    )

    private var mJobPlay: Job? = null

    fun pause() {
        isPlaying = false
        mJobPlay?.cancel()
    }

    fun play(
        atTimeMs: Long = 0L
    ) {
        if (isPlaying) {
            pause()
            return
        }

        isPlaying = true

        mJobPlay = mScope.launch {
            var currentMs = atTimeMs
            var dt: Long
            mScope.remember()

            val options = options
                ?: return@launch

            val animator = VEAnimatorVector(
                options
            )

            val fDuration = duration.toFloat()

            while (currentMs < duration && isPlaying) {
                dt = mScope.deltaTime

                animator.tick(
                    dt / fDuration
                )

                mScrollerHorizontal.scrollValue =
                    currentMs / fDuration * -durationPx

                currentMs += dt
                mScope.remember()

                launch(
                    Dispatchers.Main
                ) {
                    invalidate()
                }
            }
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

        val tickerHeight = height * heightTickTriggerFactor
        var y = tickerHeight
        val ww = width * widthOptionFactor
        val hh = height * heightOptionFactor
        val wTimer = width - ww

        options?.forEach {
            it.let {
                it.x = 0f
                it.y = y
                it.layout(
                    ww, hh
                )
            }

            it.tickTimer.let {
                it.x = ww
                it.y = y
                it.durationPx = width
                it.layout(
                    wTimer, hh
                )
            }

            y += hh
        }

        mTicker.layout(
            0f,
            tickerHeight,
            ww,
            width.toFloat()
        )

        mScrollerHorizontal.apply {
            triggerEndY = tickerHeight
            triggerEndX = ww
        }

        mScrollerVertical.triggerEndX = ww * 0.5f

        mPaintText.textSize = tickerHeight * 0.18f

        duration = 5000
    }

    override fun onDraw(
        canvas: Canvas
    ) = canvas.run {

        super.onDraw(
            canvas
        )

        var tickX = 0f
        var tickY = mPaintText.textSize

        options?.firstOrNull()?.apply {
            tickX = tickTimer.x
            tickY += tickTimer.y
        }

        save()

        translate(
            0f,
            mScrollerVertical.scrollValue
        )

        options?.forEach {
            it.draw(
                canvas
            )

            it.tickTimer.apply {
                scrollTimer = mScrollerHorizontal.scrollValue
                draw(canvas)
            }
        }

        for (it in mTimeDividers) {
            drawText(
                it.time,
                tickX + it.scrollPosition + mScrollerHorizontal.scrollValue,
                tickY,
                mPaintText
            )
        }

        val scrollDuration = ((abs(
            mScrollerHorizontal.scrollValue
        ) + mTicker.tickPosition) / durationPx * duration).toInt()

        drawText(
            scrollDuration.toString(),
            tickX + mTicker.tickPosition,
            height.toFloat(),
            mPaintText
        )

        restore()

        mTicker.draw(
            this
        )

        drawText(
            "interpolation: $interpolation",
            0f,
            height - mPaintDebugText.textSize,
            mPaintDebugText
        )

        drawText(
            "subInterpolation: $subInterpolation",
            0f,
            height - mPaintDebugText.textSize*2,
            mPaintDebugText
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

        if (event.action == MotionEvent.ACTION_UP) {
            mScrollerVertical.apply {
                options?.forEach {
                    val y = it.y + scrollValue
                    if (y > 0
                        && event.x < it.width
                        && event.y.isInRange(
                            y,
                            y+it.height
                    )) {
                        val fa = (abs(mScrollerHorizontal
                            .scrollValue
                        ) + mTicker.tickPosition) / durationPx

                        it.tickTimer.tick(
                            duration,
                            fa
                        )
                    }
                }
            }

            invalidate()
        }

        return true
    }
}

private data class TimeDivider(
    val scrollPosition: Float,
    val time: String
)