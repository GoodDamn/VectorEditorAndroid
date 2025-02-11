package good.damn.editor.editmodes

import android.graphics.Canvas
import android.graphics.PointF
import android.view.MotionEvent
import good.damn.editor.actions.VEDataActionPosition
import good.damn.editor.actions.VEDataActionShape
import good.damn.editor.actions.VEDataActionSkeletonPoint
import good.damn.editor.actions.VEIActionable
import good.damn.sav.core.listeners.VEICallbackOnAddShape
import good.damn.sav.core.listeners.VEICallbackOnAddSkeletonPoint
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectShape
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.primitives.VEShapeLine
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.extensions.interpolate
import good.damn.sav.core.extensions.interpolateWith
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.misc.interfaces.VEIDrawable
import good.damn.sav.misc.interfaces.VEITouchable
import java.util.LinkedList

class VEEditModeShape(
    private val anchor: VEAnchor,
    val canvasWidth: Float,
    val canvasHeight: Float
): VEITouchable,
VEIDrawable,
VEICallbackOnAddSkeletonPoint,
VEICallbackOnAddShape,
VEIListenerOnAnchorPoint {

    companion object {
        private val TAG = VEEditModeShape::class.simpleName
    }

    var onSelectShape: VEIListenerOnSelectShape? = null

    var currentPrimitive: VEShapeBase = VEShapeLine()

    val shapes = VEListShapes().apply {
        onAddShape = this@VEEditModeShape
    }

    var vectorStrokeWidth = 5f
    var vectorFill: VEIFill? = null

    private val mActions = LinkedList<VEIActionable>()

    val skeleton = VESkeleton2D(
        LinkedList()
    ).apply {
        onAddSkeletonPoint = this@VEEditModeShape
    }

    private var mPointFrom: VEPointIndexed? = null
    private var mPointTo: VEPointIndexed? = null

    private var mPointMiddle: VEPointIndexed? = null

    private var mTouchX = 0f
    private var mTouchY = 0f

    override fun onTouchEvent(
        event: MotionEvent
    ): Boolean {
        mTouchX = event.x
        mTouchY = event.y

        when(
            event.action
        ) {
            MotionEvent.ACTION_DOWN -> {
                val tempX = mTouchX
                val tempY = mTouchY

                anchor.isNoDrawAnchors = false

                mPointFrom = skeleton.find(
                    tempX,
                    tempY
                )

                if (mPointFrom == null) {
                    shapes.find(
                        mTouchX,
                        mTouchY
                    )?.apply {
                        onSelectShape?.onSelectShape(
                            this
                        )
                        return false
                    }
                }

                if (mPointFrom != null) {
                    mActions.add(
                        VEDataActionPosition(
                            mPointFrom!!
                        )
                    )
                }

                if (mPointFrom == null) {
                    mPointFrom = VEPointIndexed(
                        tempX,
                        tempY
                    ).apply {
                        skeleton.addSkeletonPoint(
                            this
                        )

                        mActions.add(
                            VEDataActionPosition(
                                this
                            )
                        )
                    }
                }

                mPointTo = VEPointIndexed(
                    tempX,
                    tempY
                ).apply {
                    // New point
                    // New primitive
                    skeleton.addSkeletonPoint(
                        this
                    )

                    mActions.add(
                        VEDataActionPosition(
                            this
                        )
                    )
                }

                vectorFill = currentPrimitive.fill
                vectorStrokeWidth = currentPrimitive.strokeWidth

                currentPrimitive = currentPrimitive.newInstance(
                    canvasWidth,
                    canvasHeight
                ).apply {
                    fill = vectorFill
                    strokeWidth = vectorStrokeWidth

                    points[0] = mPointFrom
                    points[1] = mPointTo

                    if (points.size == 3) {
                        mPointMiddle = mPointFrom?.interpolateWith(
                            0.5f,
                            mPointTo
                        )?.apply {
                            skeleton.addSkeletonPoint(
                                this
                            )
                        }

                        points[1] = mPointMiddle
                        points[2] = mPointTo
                    }

                    if (mPointFrom != null) {
                        shapes.add(
                            this
                        )
                    }
                }
            }

            MotionEvent.ACTION_MOVE -> {
                mPointTo?.let { to ->
                    anchor.checkAnchors(
                        skeleton,
                        mTouchX,
                        mTouchY,
                        to.id.id
                    )

                    mPointFrom?.let { from ->
                        mPointMiddle?.interpolate(
                            0.5f,
                            from,
                            to
                        )
                    }
                }
            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                anchor.isNoDrawAnchors = true
                mPointTo?.let { to ->
                    skeleton.find(
                        event.x,
                        event.y
                    )?.apply {
                        if (id == to.id) {
                            return@let
                        }
                        currentPrimitive.points[1] = this
                        skeleton.removeLast()
                    }
                }

                mPointTo = null
                mPointMiddle = null
                mPointFrom = null
            }
        }

        return true
    }

    override fun draw(
        canvas: Canvas
    ) {
        anchor.draw(
            canvas
        )
    }

    override fun onAddSkeletonPoint(
        point: PointF
    ) {
        mActions.add(
            VEDataActionSkeletonPoint(
                skeleton
            )
        )
    }

    override fun onAddShape(
        shape: VEShapeBase
    ) {
        mActions.add(
            VEDataActionShape(
                shapes
            )
        )
    }

    override fun onAnchorX(
        x: Float
    ) {
        mPointTo?.x = x
    }

    override fun onAnchorY(
        y: Float
    ) {
        mPointTo?.y = y
    }

    fun undoAction() {
        mActions
            .removeLastOrNull()
            ?.removeAction()
    }

    fun clearActions() {
        while (
            mActions
                .removeLastOrNull()
                ?.removeAction() != null
        ) {}
    }


}