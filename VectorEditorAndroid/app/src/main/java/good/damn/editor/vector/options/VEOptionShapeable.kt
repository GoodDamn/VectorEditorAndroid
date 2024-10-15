package good.damn.editor.vector.options

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PointF
import android.view.MotionEvent
import androidx.core.content.res.TypedArrayUtils
import good.damn.editor.vector.actions.VEDataActionPosition
import good.damn.editor.vector.actions.VEDataActionShape
import good.damn.editor.vector.actions.VEDataActionSkeletonPoint
import good.damn.editor.vector.actions.VEIActionable
import good.damn.editor.vector.actions.callbacks.VEICallbackOnAddShape
import good.damn.editor.vector.actions.callbacks.VEICallbackOnAddSkeletonPoint
import good.damn.editor.vector.anchors.VEAnchor
import good.damn.editor.vector.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.vector.extensions.interpolate
import good.damn.editor.vector.interfaces.VEIDrawable
import good.damn.editor.vector.interfaces.VEITouchable
import good.damn.editor.vector.lists.VEListShapes
import good.damn.editor.vector.shapes.VEShapeBase
import good.damn.editor.vector.shapes.VEShapeLine
import good.damn.editor.vector.points.VEPointIndexed
import good.damn.editor.vector.skeleton.VESkeleton2D
import java.util.LinkedList

class VEOptionShapeable(
    private val mAnchor: VEAnchor,
    val canvasWidth: Float,
    val canvasHeight: Float
): VEITouchable,
VEIDrawable,
VEICallbackOnAddSkeletonPoint,
VEICallbackOnAddShape,
VEIListenerOnAnchorPoint {

    companion object {
        private const val TAG = "VEOptionPrimitivable"
    }

    var currentPrimitive: VEShapeBase = VEShapeLine(
        canvasWidth,
        canvasHeight
    )

    val shapes = VEListShapes().apply {
        onAddShape = this@VEOptionShapeable
    }

    var vectorStrokeWidth = 5f
    var vectorColor = Color.RED

    private val mActions = LinkedList<VEIActionable>()

    val skeleton = VESkeleton2D(
        LinkedList()
    ).apply {
        onAddSkeletonPoint = this@VEOptionShapeable
    }

    private var mPointFrom: VEPointIndexed? = null
    private var mPointTo: VEPointIndexed? = null

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

                mPointFrom = skeleton.find(
                    tempX,
                    tempY
                )?.apply {
                    mActions.add(
                        VEDataActionPosition(
                            this
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

                vectorColor = currentPrimitive.color
                vectorStrokeWidth = currentPrimitive.strokeWidth

                currentPrimitive = currentPrimitive.newInstance(
                    canvasWidth,
                    canvasHeight
                ).apply {
                    color = vectorColor
                    strokeWidth = vectorStrokeWidth

                    points[0] = mPointFrom
                    points[1] = mPointTo

                    if (points.size == 3) {
                        points[1] = mPointFrom?.interpolate(
                            0.5f,
                            mPointTo
                        )?.apply {
                            skeleton.addSkeletonPoint(
                                this
                            )
                        }
                        points[2] = mPointFrom
                    }

                    if (mPointFrom != null) {
                        shapes.add(
                            this
                        )
                    }
                }
            }

            MotionEvent.ACTION_MOVE -> {
                mPointTo?.apply {
                    mAnchor.checkAnchors(
                        skeleton,
                        mTouchX,
                        mTouchY,
                        index
                    )
                }
            }

            MotionEvent.ACTION_UP -> {
                mPointFrom = null
                mPointTo = null
            }

            else -> {

            }
        }

        return true
    }

    override fun onDraw(
        canvas: Canvas
    ) {
        mAnchor.draw(
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