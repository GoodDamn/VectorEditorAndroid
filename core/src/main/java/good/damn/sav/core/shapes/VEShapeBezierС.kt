package good.damn.sav.core.shapes

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.Log
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.misc.extensions.cubicTo
import good.damn.sav.misc.extensions.moveTo
import java.io.InputStream
import java.io.OutputStream

class VEShapeBezierС(
    canvasWidth: Float,
    canvasHeight: Float
): VEShapeBase(
    canvasWidth,
    canvasHeight
) {

    companion object {
        private val TAG = VEShapeBezierС::class.simpleName
        const val ENCODE_TYPE = 1
    }

    private val mPath = Path()

    override val points = Array<
       VEPointIndexed?
    >(3) {null}

    init {
        mPaint.apply {
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
        }
    }

    override fun draw(
        canvas: Canvas
    ) = mPath.run {
        reset()

        val p0 = points[0]
            ?: return@run

        val p1 = points[1]
            ?: return@run

        val p2 = points[2]
            ?: return@run

        Log.d(TAG, "onDraw: $p0 $p1 $p2")
        
        moveTo(p0)

        cubicTo(
            p0,
            p1,
            p2
        )

        canvas.drawPath(
            this,
            mPaint
        )
    }

    override fun newInstance(
        width: Float,
        height: Float
    ) = VEShapeBezierС(
        width,
        height
    )

}