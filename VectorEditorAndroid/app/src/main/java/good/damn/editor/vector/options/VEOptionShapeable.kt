package good.damn.editor.vector.options

import android.graphics.Canvas
import android.graphics.PointF
import good.damn.editor.vector.extensions.interpolate
import good.damn.editor.vector.lists.VEListShapes
import good.damn.editor.vector.paints.VEPaintBase
import good.damn.editor.vector.paints.VEPaintLine
import good.damn.editor.vector.skeleton.VESkeleton2D
import java.util.LinkedList

class VEOptionShapeable(
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
        shapes: VEListShapes,
        selectedPoint: PointF?,
        skeleton: VESkeleton2D
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
                )?.apply {
                    skeleton.addSkeletonPoint(
                        this
                    )
                }
                points[2] = selectedPoint
            }

            if (mPrevPoint != null) {
                shapes.add(
                    this
                )
            }

            mPrevPoint = selectedPoint
        }
    }

}