package good.damn.editor.vector.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.PointF
import android.view.MotionEvent
import android.view.View
import good.damn.editor.vector.actions.VEDataActionPosition
import good.damn.editor.vector.actions.VEDataActionShape
import good.damn.editor.vector.actions.VEDataActionSkeletonPoint
import good.damn.editor.vector.actions.VEIActionable
import good.damn.editor.vector.options.VEIOptionable
import good.damn.editor.vector.actions.callbacks.VEICallbackOnAddShape
import good.damn.editor.vector.actions.callbacks.VEICallbackOnAddSkeletonPoint
import good.damn.editor.vector.anchors.VEAnchor
import good.damn.editor.vector.anchors.VEAnchorStraightVertical
import good.damn.editor.vector.lists.VEListShapes
import good.damn.editor.vector.shapes.VEShapeBase
import good.damn.editor.vector.points.VEPointIndexed
import good.damn.editor.vector.skeleton.VESkeleton2D
import java.util.LinkedList

class VEViewVector(
    context: Context,
    startOption: VEIOptionable
): View(
    context
), VEICallbackOnAddSkeletonPoint,
VEICallbackOnAddShape {

    companion object {
        private const val TAG = "VEViewVector"
    }

    var optionable: VEIOptionable = startOption

    var isAlignedHorizontal = false
    var isAlignedVertical = false

    val shapes = VEListShapes().apply {
        onAddShape = this@VEViewVector
    }

    val skeleton: VESkeleton2D
        get() = mSkeleton2D

    private val mActions = LinkedList<VEIActionable>()

    private val mSkeleton2D = VESkeleton2D(
        LinkedList()
    ).apply {
        onAddSkeletonPoint = this@VEViewVector
    }

    private val mAnchor = VEAnchor()
    private var mSelectedPoint: VEPointIndexed? = null

    private val mPointMove = PointF()

    override fun onDraw(
        canvas: Canvas
    ) {
        super.onDraw(
            canvas
        )

        mSkeleton2D.onDraw(
            canvas
        )

        shapes.forEach {
            it.onDraw(
                canvas
            )
        }

        mSelectedPoint?.let {
            mAnchor.checkAnchors(
                canvas,
                mSkeleton2D,
                it
            )
        }
    }

    override fun onTouchEvent(
        event: MotionEvent?
    ): Boolean {
        if (event == null) {
            return false
        }

        when (
            event.action
        ) {
            MotionEvent.ACTION_DOWN -> {
                val tempX = event.x
                val tempY = event.y

                mSelectedPoint = mSkeleton2D.find(
                    tempX,
                    tempY
                )

                if (mSelectedPoint == null) {
                    // New point
                    // New primitive
                    mSkeleton2D.addSkeletonPoint(
                        VEPointIndexed(
                            tempX,
                            tempY
                        )
                    )
                    mSelectedPoint = mSkeleton2D
                        .getLastPoint()
                }

                mSelectedPoint?.let {
                    mActions.add(
                        VEDataActionPosition(
                            it
                        )
                    )
                }

                optionable.runOption(
                    shapes,
                    mSelectedPoint,
                    mSkeleton2D
                )

                invalidate()
            }

            MotionEvent.ACTION_MOVE -> {
                if (isAlignedHorizontal) {
                    mPointMove.x = event.x
                }

                if (isAlignedVertical) {
                    mPointMove.y = event.y
                }

                mSelectedPoint?.set(
                    mPointMove
                )

                invalidate()
            }
        }


        return true
    }

    fun undoAction() {
        mActions
            .removeLastOrNull()
            ?.removeAction()
        invalidate()
    }

    fun clearActions() {
        while (
            mActions
                .removeLastOrNull()
                ?.removeAction() != null
        ) {}
        invalidate()
    }

    fun setSkeleton(
        points: MutableList<VEPointIndexed>
    ) {
        mSkeleton2D.resetSkeleton(
            points
        )
    }

    override fun onAddSkeletonPoint(
        point: PointF
    ) {
        mActions.add(
            VEDataActionSkeletonPoint(
                mSkeleton2D
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
}