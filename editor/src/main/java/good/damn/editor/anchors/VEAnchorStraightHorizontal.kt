package good.damn.editor.anchors

import android.graphics.Canvas
import android.graphics.PointF
import good.damn.editor.anchors.internal.VEAnchorInternalBase
import good.damn.editor.anchors.internal.VEAnchorInternalPropLenHorizontal
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.extensions.drawLine
import kotlin.math.abs

class VEAnchorStraightHorizontal
: VEBaseAnchor() {

    companion object {
        private const val TAG = "VEAnchorStraightHorizon"
    }

    private var mPointFrom: PointF? = null
    private var mPointTo: PointF? = null

    private val mPointTouch = PointF()

    private val mInternalAnchors: Array<
        VEAnchorInternalBase
    > = arrayOf(
        VEAnchorInternalPropLenHorizontal(
            lineWidth = 15f
        )
    )

    override fun onDraw(
        canvas: Canvas
    ) {
        mPointFrom?.let { from ->
            mPointTo?.let {
                canvas.drawLine(
                    from,
                    it,
                    mPaint
                )
            }
        }


        mInternalAnchors.forEach {
            if (it.isPreparedToDraw) {
                it.draw(canvas)
            }
        }
    }

    override fun checkAnchor(
        skeleton: VESkeleton2D,
        touchX: Float,
        touchY: Float
    ): Boolean {
        var minRightX = Float.MAX_VALUE
        var minLeftX = 0f

        mPointFrom = null
        mPointTo = null

        mPointTouch.set(
            touchX,
            touchY
        )

        for (it in skeleton.points) {
            if (abs(it.y - mPointTouch.y) < 30) { // px
                mPointTouch.y = it.y
                onAnchorPoint?.onAnchorY(
                    it.y
                )

                if (minRightX > it.x && it.x > mPointTouch.x) {
                    minRightX = it.x
                    mPointTo = it
                }

                if (minLeftX < it.x && it.x < mPointTouch.x) {
                    minLeftX = it.x
                    mPointFrom = it
                }
            }
        }

        if (mPointFrom == null && mPointTo == null) {
            return false
        }

        if (mPointFrom == null) {
            mPointFrom = mPointTouch
        }

        if (mPointTo == null) {
            mPointTo = mPointTouch
        }

        for (it in mInternalAnchors) {
            val p = it.checkAnchor(
                mPointFrom!!,
                mPointTo!!,
                mPointTouch
            )

            if (p == null) {
                it.isPreparedToDraw = false
                continue
            }
            it.isPreparedToDraw = true

            onAnchorPoint?.apply {
                onAnchorX(p.x)
                onAnchorY(p.y)
            }
        }

        return true
    }
}