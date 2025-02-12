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
import good.damn.sav.core.shapes.fill.VEMFillColor
import good.damn.sav.core.shapes.fill.VEMFillGradientLinear
import good.damn.sav.misc.extensions.toInt32

class VEBottomSheetMakeGradient(
    private val toView: ViewGroup,
    private val p: PointF?,
    private val pp: PointF?,
    private val onConfirmFill: VEIListenerBottomSheetFill
): VEBottomSheet(
    toView
) {

    private val mColors = IntArray(3)
    private val mPositions = floatArrayOf(
        0.0f,
        0.5f,
        1.0f
    )

    override fun onCreateView(
        context: Context
    ) = LinearLayout(
        context
    ).apply {

        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER

        val w = (VEApp.width * 0.1f).toInt()
        addColorView(
            toView,
            this,
            w,
            mColors,
            0
        )

        addColorView(
            toView,
            this,
            w,
            mColors,
            1
        )

        addColorView(
            toView,
            this,
            w,
            mColors,
            2
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

    override fun dismiss() {
        p ?: return
        pp ?: return
        onConfirmFill.onConfirmFill(
            VEMFillGradientLinear(
                p,
                pp,
                mColors,
                mPositions
            )
        )
        super.dismiss()
    }

}

private inline fun addColorView(
    toView: ViewGroup,
    root: LinearLayout,
    w: Int,
    mColors: IntArray,
    index: Int
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
            mColors[index] = (it as? VEMFillColor)?.color?.toInt32() ?: 0
            setBackgroundColor(
                mColors[index]
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