package good.damn.editor.vector.anchors

import android.graphics.Paint

abstract class VEBaseAnchor
: VEIAnchorable {

    protected val mPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 5f
    }

}