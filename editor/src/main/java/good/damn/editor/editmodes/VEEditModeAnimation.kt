package good.damn.editor.editmodes

import android.graphics.Matrix
import android.view.MotionEvent
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectPoint
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectShape
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.skeleton.VESkeleton2D

class VEEditModeAnimation(
    private val shapes: VEListShapes,
    private val skeleton: VESkeleton2D,
    private val anchor: VEAnchor
): VEEditModeTransformed(),
VEIListenerOnAnchorPoint {

    var onSelectPoint: VEIListenerOnSelectPoint? = null
    var onSelectShape: VEIListenerOnSelectShape? = null

    private var mSelectedPoint: VEPointIndexed? = null

    override fun onTouchEvent(
        event: MotionEvent,
        invertedMatrix: Matrix
    ): Boolean {
        super.onTouchEvent(
            event,
            invertedMatrix
        )

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                anchor.isNoDrawAnchors = false
                mSelectedPoint = skeleton.find(
                    event.x,
                    event.y
                )

                mSelectedPoint?.apply {
                    onSelectPoint?.onSelectPoint(
                        this
                    )
                    return true
                }

                shapes.find(
                    event.x,
                    event.y
                )?.apply {
                    onSelectShape?.onSelectShape(
                        this
                    )
                    return true
                }

                return false
            }

            MotionEvent.ACTION_MOVE -> {
                mSelectedPoint?.apply {
                    anchor.checkAnchors(
                        skeleton,
                        event.x,
                        event.y,
                        id.id
                    )
                }

                return true
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                anchor.isNoDrawAnchors = true
            }
        }

        return true
    }

    override fun onAnchorX(
        x: Float
    ) {
        mSelectedPoint?.x = x
    }

    override fun onAnchorY(
        y: Float
    ) {
        mSelectedPoint?.y = y
    }
}