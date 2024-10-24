package good.damn.editor.anchors

import android.graphics.Canvas
import android.graphics.PointF
import android.util.Log
import good.damn.editor.anchors.internal.VEAnchorInternalBase
import good.damn.editor.anchors.internal.VEAnchorInternalPropLenVertical
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.extensions.drawLine
import kotlin.math.abs
import kotlin.math.cos

class VEAnchorStraightVertical
: VEBaseAnchor() {

    companion object {
        private const val TAG = "VEAnchorStraightVertica"
    }

    private var mPointFrom: PointF? = null
    private var mPointTo: PointF? = null

    private val mPointTouch = PointF()

    private val mAnchorsInternal: Array<
            VEAnchorInternalBase
    > = arrayOf(
        VEAnchorInternalPropLenVertical(
            lineWidth = 20f
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

        mAnchorsInternal.forEach {
            if (it.isPreparedToDraw) {
                it.draw(canvas)
            }
        }
    }

    override fun checkAnchor(
        skeleton: good.damn.sav.core.skeleton.VESkeleton2D,
        touchX: Float,
        touchY: Float
    ): Boolean {
        var minDownY = Float.MAX_VALUE
        var minUpY = 0f

        mPointTouch.set(
            touchX,
            touchY
        )

        mPointFrom = null
        mPointTo = null

        for (it in skeleton.points) {

            if (selectedIndex == it.index) {
                continue
            }

            if (abs(it.x - mPointTouch.x) < 30) { // px
                mPointTouch.x = it.x
                onAnchorPoint?.onAnchorX(
                    it.x
                )

                if (minDownY > it.y && it.y > mPointTouch.y) {
                    minDownY = it.y
                    mPointTo = it
                }

                if (minUpY < it.y && it.y < mPointTouch.y) {
                    minUpY = it.y
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

        for (it in mAnchorsInternal) {
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