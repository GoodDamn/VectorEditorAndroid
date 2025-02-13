package good.damn.editor.vector.bottomsheets

import android.content.Context
import android.graphics.PointF
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.bottomsheets.listeners.VEIListenerBottomSheetFill
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.core.shapes.fill.VEMFillColor
import good.damn.sav.core.shapes.fill.VEMFillGradientLinear
import good.damn.sav.misc.extensions.toInt32

class VEBottomSheetMakeGradient(
    private val toView: ViewGroup,
    private val p: PointF?,
    private val pp: PointF?,
    private val onConfirmFill: VEIListenerBottomSheetFill<VEMFillGradientLinear>
): VEBottomSheet(
    toView
) {

    companion object {
        fun makeView(
            context: Context,
            toView: ViewGroup,
            colors: IntArray,
            positions: FloatArray,
            p: PointF?,
            pp: PointF?,
            onConfirmFill: VEIListenerBottomSheetFill<VEMFillGradientLinear>
        ) = LinearLayout(
            context
        ).apply {

            p ?: return@apply
            pp ?: return@apply

            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER

            val w = (VEApp.width * 0.1f).toInt()
            addColorView(
                toView,
                this,
                w,
                colors,
                positions,
                0,
                onConfirmFill,
                p,
                pp
            )

            addColorView(
                toView,
                this,
                w,
                colors,
                positions,
                1,
                onConfirmFill,
                p,
                pp
            )

            addColorView(
                toView,
                this,
                w,
                colors,
                positions,
                2,
                onConfirmFill,
                p,
                pp
            )

            setBackgroundColor(
                0xff000315.toInt()
            )

            boundsFrame(
                gravity = Gravity.BOTTOM,
                bottom = VEApp.height * 0.1f,
                width = VEApp.width.toFloat(),
                height = VEApp.height * 0.3f
            )
        }
    }

    private val mPositions = floatArrayOf(
        0.0f,
        0.5f,
        1.0f
    )

    private val mColors = IntArray(
        mPositions.size
    )

    override fun onCreateView(
        context: Context
    ) = makeView(
        context,
        toView,
        mColors,
        mPositions,
        p,
        pp,
        onConfirmFill
    )

}

private inline fun addColorView(
    toView: ViewGroup,
    root: LinearLayout,
    w: Int,
    mColors: IntArray,
    mPositions: FloatArray,
    index: Int,
    onConfirmFill: VEIListenerBottomSheetFill<VEMFillGradientLinear>,
    p: PointF,
    pp: PointF
) = View(
    toView.context
).apply {
    setBackgroundColor(
        0xffffffff.toInt()
    )
    setOnClickListener {
        VEBottomSheetSelectColor(
            toView
        ) {
            mColors[index] = it?.color?.toInt32() ?: 0
            setBackgroundColor(
                mColors[index]
            )

            onConfirmFill.onConfirmFill(
                VEMFillGradientLinear(
                    p.x,
                    p.y,
                    pp.x,
                    pp.y,
                    mColors,
                    mPositions
                )
            )
        }.show()
    }

    layoutParams = LinearLayout.LayoutParams(
        w, w
    ).apply {
        leftMargin = (w * 0.25f).toInt()
    }

    root.addView(this)
}