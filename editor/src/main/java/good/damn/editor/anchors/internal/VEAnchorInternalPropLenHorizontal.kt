package good.damn.editor.anchors.internal

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import good.damn.editor.anchors.VEMProjectionAnchor
import kotlin.math.abs
import kotlin.math.hypot

class VEAnchorInternalPropLenHorizontal(
    private val projection: VEMProjectionAnchor
): VEAnchorInternalBase() {

    companion object {
        private const val TAG = "VEAnchorInternalPropLen"
    }

    private val mPaint = Paint().apply {
        color = 0x99ffff00.toInt()
        strokeWidth = projection.lineWidth
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
            mx + 0.01f,
            my2,
            mPaint
        )

        canvas.drawLine(
            mx2,
            my,
            mx2 + 0.01f,
            my2,
            mPaint
        )

    }

    override fun checkAnchor(
        from: PointF,
        to: PointF,
        touch: PointF
    ): PointF? {

        val len = abs(
            from.x - to.x
        )

        mPointTarget.x = from.x + len * 0.5f
        mPointTarget.y = from.y

        if (hypot(
            touch.x - mPointTarget.x,
            touch.y - mPointTarget.y
        ) < projection.propLenScaled) {
            my = from.y - projection.propMiddlePointLenScaled
            my2 = from.y + projection.propMiddlePointLenScaled

            mx = from.x + len * 0.25f
            mx2 = from.x + len * 0.75f
            return mPointTarget
        }

        return null
    }
}