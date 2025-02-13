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
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.core.shapes.fill.VEMFillColor
import good.damn.sav.core.shapes.fill.VEMFillGradientLinear
import good.damn.sav.misc.extensions.toInt32

class VEBottomSheetSetupShape(
    private val toView: ViewGroup,
    private val currentFill: VEIFill?,
    private val shapes: VEListShapes,
    private val p: PointF?,
    private val pp: PointF?,
    private val onConfirmFill: VEIListenerBottomSheetFill<VEIFill>
): VEBottomSheet(
    toView
) {
    var onSeekProgressWidth: VSIListenerSeekBarProgress? = null

    override fun onCreateView(
        context: Context
    ) = FrameLayout(
        context
    ).let { root ->

        val rootWidth = VEApp.width.toFloat()
        val rootHeight = VEApp.height * 0.5f

        root.boundsFrame(
            top = VEApp.height - rootHeight,
            width = rootWidth,
            height = rootHeight
        )

        root.setBackgroundColor(
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

            root.addView(this)
        }

        LinearLayout(
            context
        ).apply {

            orientation = LinearLayout.VERTICAL

            Button(
                context
            ).apply {
                text = "color"

                setOnClickListener {
                    VEBottomSheetSelectColor(
                        toView
                    ) {
                        onConfirmFill.onConfirmFill(it)
                    }.apply {
                        show()
                    }
                }


                addView(
                    this,
                    -1,
                    -2
                )
            }

            Button(
                context
            ).apply {
                text = "gradient"

                setOnClickListener {
                    VEBottomSheetMakeGradient(
                        toView,
                        p,
                        pp
                    ) {
                        onConfirmFill.onConfirmFill(it)
                    }.show()
                }

                addView(
                    this,
                    -1,
                    -2
                )
            }

            currentFill?.let { fill ->

                when (fill) {
                    is VEMFillColor -> addView(
                        placeColorView(
                            shapes,
                            toView,
                            fill,
                            onConfirmFill
                        )
                    )
                    is VEMFillGradientLinear -> {
                        addView(
                            VEBottomSheetMakeGradient.makeView(
                                toView.context,
                                toView,
                                fill.colors,
                                fill.positions,
                                p,
                                pp
                            ) {
                                shapes.forEach { shape ->
                                    shape.fill = it
                                }
                                onConfirmFill.onConfirmFill(it)
                            }
                        )
                    }
                }
            }

            boundsFrame(
                start = rootWidth * 0.2f,
                width = rootWidth * 0.8f,
                height = -2f
            )

            root.addView(
                this
            )
        }

        return@let root
    }

}

private inline fun placeColorView(
    shapes: VEListShapes,
    toView: ViewGroup,
    fill: VEMFillColor,
    onConfirmFill: VEIListenerBottomSheetFill<VEIFill>
) = View(
    toView.context
).apply {

    setBackgroundColor(
        fill.color.toInt32()
    )

    setOnClickListener {
        VEBottomSheetSelectColor(
            toView
        ) {
            fill.color = it?.color ?: byteArrayOf(0)
            setBackgroundColor(
                fill.color.toInt32()
            )
            shapes.forEach { shape ->
                shape.fill = fill
            }
            onConfirmFill.onConfirmFill(fill)
        }.show()
    }

    val s = VEApp.width * 0.2f
    bounds(s, s)
}