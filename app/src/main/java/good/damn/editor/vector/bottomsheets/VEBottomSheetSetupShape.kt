package good.damn.editor.vector.bottomsheets

import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.bottomsheets.listeners.VEIListenerBottomSheetFill
import good.damn.editor.vector.extensions.views.bounds
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.editor.vector.extensions.views.boundsLinear
import good.damn.gradient_color_picker.GradientColorPicker
import good.damn.gradient_color_picker.OnPickColorListener
import good.damn.lib.verticalseekbar.VSViewSeekBarV
import good.damn.lib.verticalseekbar.interfaces.VSIListenerSeekBarProgress
import good.damn.sav.core.shapes.fill.VEMFillColor
import good.damn.sav.core.shapes.fill.VEMFillGradientLinear

class VEBottomSheetSetupShape(
    private val toView: ViewGroup,
    private val p: PointF?,
    private val pp: PointF?,
    private val onConfirmFill: VEIListenerBottomSheetFill
): VEBottomSheet(
    toView
) {
    var onSeekProgressWidth: VSIListenerSeekBarProgress? = null

    override fun onCreateView(
        context: Context
    ) = FrameLayout(
        context
    ).apply {

        val rootWidth = VEApp.width.toFloat()
        val rootHeight = VEApp.height * 0.5f

        boundsFrame(
            top = VEApp.height - rootHeight,
            width = rootWidth,
            height = rootHeight
        )

        setBackgroundColor(
            0xff000315.toInt()
        )

        VSViewSeekBarV(
            context
        ).apply {
            onSeekProgress = onSeekProgressWidth

            boundsFrame(
                0f,
                start = 0f,
                width = rootWidth * 0.2f,
                height = rootHeight
            )

            setBackgroundColor(
                Color.GRAY
            )

            strokeWidth = 15f

            progress = 0.1f
            progressColor = 0xffffffff.toInt()

            addView(this)
        }


        val btnHeight = rootHeight * 0.12f
        Button(
            context
        ).apply {
            text = "color"

            setOnClickListener {
                VEBottomSheetSelectColor(
                    toView,
                    onConfirmFill
                ).apply {
                    show()
                }
            }

            val width = rootWidth * 0.8f
            boundsFrame(
                start = rootWidth - width,
                width = width,
                height = btnHeight
            )

            addView(
                this
            )
        }

        Button(
            context
        ).apply {
            text = "gradient"

            val width = rootWidth * 0.8f
            boundsFrame(
                top = btnHeight,
                start = rootWidth - width,
                width = width,
                height = btnHeight
            )

            setOnClickListener {
                VEBottomSheetMakeGradient(
                    toView,
                    p,
                    pp,
                    onConfirmFill
                ).show()
            }

            addView(this)
        }

    }

}