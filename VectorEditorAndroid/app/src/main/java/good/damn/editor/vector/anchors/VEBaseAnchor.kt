package good.damn.editor.vector.anchors

import android.graphics.Paint
import good.damn.editor.vector.anchors.listeners.VEIListenerOnAnchorPoint

abstract class VEBaseAnchor
: VEIAnchorable {

    var onAnchorPoint: VEIListenerOnAnchorPoint? = null

    var isPreparedToDraw = false

    var selectedIndex: Int = 0

    protected val mPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 5f
    }

}