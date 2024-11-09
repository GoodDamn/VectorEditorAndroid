package good.damn.editor.vector.bottomsheets

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.extensions.views.bounds
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.editor.vector.extensions.views.boundsLinear
import good.damn.gradient_color_picker.GradientColorPicker
import good.damn.gradient_color_picker.OnPickColorListener
import good.damn.lib.verticalseekbar.VSViewSeekBarV
import good.damn.lib.verticalseekbar.interfaces.VSIListenerSeekBarProgress

class VEBottomSheetSetupShape(
    toView: ViewGroup
): VEBottomSheet(
    toView
) {

    var onSeekProgressWidth: VSIListenerSeekBarProgress? = null
    var onPickColor: OnPickColorListener? = null

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

        GradientColorPicker(
            context
        ).apply {

            setOnPickColorListener(
                onPickColor
            )

            val start = rootWidth * 0.2f

            boundsFrame(
                start = start,
                width = rootWidth - start,
                height = rootHeight * 0.5f
            )

            addView(this)
        }


    }

}