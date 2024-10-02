package good.damn.editor.vector.options

import android.graphics.Canvas
import android.graphics.PointF
import android.util.Log
import androidx.annotation.ColorInt
import good.damn.editor.vector.extensions.interpolate
import good.damn.editor.vector.interfaces.VEIOptionable
import good.damn.editor.vector.paints.VEPaintBase
import good.damn.editor.vector.paints.VEPaintLine
import java.util.LinkedList

class VEOptionPrimitivable(
    val canvasWidth: Float,
    val canvasHeight: Float
): VEIOptionable {

    companion object {
        private const val TAG = "VEOptionPrimitivable"
    }

    var currentPrimitive: VEPaintBase = VEPaintLine(
        canvasWidth,
        canvasHeight
    )

    private var mPrevPoint: PointF? = null

    constructor(
        size: Float
    ): this(
        size,
        size
    )

    override fun onDraw(
        canvas: Canvas
    ) = Unit

    override fun runOption(
        primitives: LinkedList<VEPaintBase>,
        selectedPoint: PointF?
    ) {
        if (mPrevPoint == null) {
            mPrevPoint = selectedPoint
            return
        }

        val savedColor = currentPrimitive.color
        val savedStrokeWidth = currentPrimitive.strokeWidth

        currentPrimitive = currentPrimitive.newInstance(
            canvasWidth,
            canvasHeight
        ).apply {
            color = savedColor
            strokeWidth = savedStrokeWidth

            points[0] = mPrevPoint
            points[1] = selectedPoint

            if (points.size == 3) {
                points[1] = mPrevPoint?.interpolate(
                    0.5f,
                    selectedPoint
                )
                points[2] = selectedPoint
            }

            if (mPrevPoint != null) {
                primitives.add(
                    this
                )
            }

            mPrevPoint = selectedPoint
        }
    }

}