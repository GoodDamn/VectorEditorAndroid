package good.damn.editor.vector.anchors.internal

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import kotlin.math.abs
import kotlin.math.hypot

class VEAnchorInternalPropLenVertical(
    private val lineWidth: Float
): VEAnchorInternalBase() {


    private val mPaint = Paint().apply {
        color = 0x99ffff00.toInt()
        strokeWidth = lineWidth
        strokeCap = Paint.Cap.ROUND
        style = Paint.Style.STROKE
    }

    private var mx = 0f
    private var mx2 = 0f

    private var my = 0f
    private var my2 = 0f

    private val mPointTarget = PointF()

    override fun draw(
        canvas: Canvas
    ) {
        // 0.01f because it's equaled values
        // and it doesn't work
        canvas.drawLine(
            mx,
            my,
            mx2,
            my + 0.01f,
            mPaint
        )

        canvas.drawLine(
            mx,
            my2,
            mx2,
            my2 + 0.01f,
            mPaint
        )
    }

    override fun checkAnchor(
        from: PointF,
        to: PointF,
        touch: PointF
    ): PointF? {
        val len = abs(
            from.y - to.y
        )

        mPointTarget.x = from.x
        mPointTarget.y = from.y + len * 0.5f

        if (hypot(
            touch.x - mPointTarget.x,
            touch.y - mPointTarget.y
        ) < 35) {
            mx = from.x - 15
            mx2 = from.x + 15

            my = from.y + len * 0.25f
            my2 = from.y + len * 0.75f
            return mPointTarget
        }

        return null
    }
}