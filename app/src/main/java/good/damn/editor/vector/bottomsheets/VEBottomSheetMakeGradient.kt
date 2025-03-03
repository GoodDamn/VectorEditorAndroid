package good.damn.editor.vector.bottomsheets

import android.content.Context
import android.graphics.PointF
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.bottomsheets.listeners.VEIListenerBottomSheetFill
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.editor.vector.view.gradient.VECanvasColorSeek
import good.damn.editor.vector.view.gradient.VEMGradientEdit
import good.damn.editor.vector.view.gradient.interfaces.VEIListenerOnGradientPosition
import good.damn.editor.vector.view.gradient.interfaces.VEIListenerOnGradientShader
import good.damn.editor.vector.view.gradient.VEViewGradientMaker
import good.damn.editor.vector.view.gradient.VEViewGradientPlacer
import good.damn.editor.vector.view.gradient.interfaces.VEIListenerOnGradientColorSeek
import good.damn.sav.core.shapes.fill.VEMFillGradientLinear
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.toInt32

class VEBottomSheetMakeGradient(
    private val canvasSize: Size,
    private val toView: ViewGroup,
    currentFill: VEMFillGradientLinear?,
    private val onConfirmFill: VEIListenerBottomSheetFill<VEMFillGradientLinear>
) : VEBottomSheet(
    toView
), VEIListenerOnGradientShader,
    VEIListenerOnGradientPosition,
    VEIListenerOnGradientColorSeek {


    private val mw = VEApp.width * 0.5f
    private val mwplacer = VEApp.height * 0.2f

    private var mViewGradMaker: VEViewGradientMaker? = null
    private var mViewGradPlacer: VEViewGradientPlacer? = null

    private val mGradientEdit = currentFill?.run {
        VEMGradientEdit(
            PointF(
                p0x / canvasSize.width * mwplacer,
                p0y / canvasSize.height * mwplacer
            ),
            PointF(
                p1x / canvasSize.width * mwplacer,
                p1y / canvasSize.height * mwplacer
            ),
            colors,
            positions
        )
    } ?: VEMGradientEdit(
        colors = intArrayOf(
            0xffff0000.toInt(),
            0xff00ff00.toInt()
        ),
        positions = floatArrayOf(
            0.0f,
            1.0f
        )
    )

    private val mColorsSeek = ArrayList<VECanvasColorSeek>(
        mGradientEdit.colors.size
    ).apply {
        for (i in mGradientEdit.colors.indices) {
            add(
                VECanvasColorSeek().apply {
                    color = mGradientEdit.colors[i]
                    rectColor
                }
            )
        }
    }

    override fun onCreateView(
        context: Context
    ) = FrameLayout(
        context
    ).apply {

        setBackgroundColor(
            0xff000315.toInt()
        )

        mViewGradMaker = VEViewGradientMaker(
            mGradientEdit,
            context
        ).apply {

            onGradientShader = this@VEBottomSheetMakeGradient
            onSelectColor = this@VEBottomSheetMakeGradient

            setInitialValues(
                mColorsSeek
            )

            addView(
                this,
                mw.toInt(),
                -1
            )
        }

        mViewGradPlacer = VEViewGradientPlacer(
            mGradientEdit,
            context
        ).apply {

            onChangePosition = this@VEBottomSheetMakeGradient
            boundsFrame(
                width = mwplacer,
                height = mwplacer,
                start = mw
            )

            addView(
                this
            )
        }

        val wb = VEApp.width * 0.1f
        Button(
            context
        ).apply {

            text = "+"

            setOnClickListener {
                mColorsSeek.add(
                    VECanvasColorSeek().apply {
                        color = 0xffffffff.toInt()
                    }
                )

                mGradientEdit.colors = IntArray(
                    mGradientEdit.colors.size + 1
                ).run {
                    for (i in mGradientEdit.colors.indices) {
                        this[i] = mGradientEdit.colors[i]
                    }
                    this[size - 1] = 0xffffffff.toInt()
                    this
                }

                mGradientEdit.positions = FloatArray(
                    mGradientEdit.positions.size + 1
                ).run {
                    for (i in mGradientEdit.positions.indices) {
                        this[i] = mGradientEdit.positions[i]
                    }

                    this[size - 1] = 1.0f
                    this
                }

                mViewGradMaker?.apply {
                    layoutColorSeekById(
                        mColorsSeek.size - 1
                    )
                    setInitialValues(
                        mColorsSeek
                    )
                    invalidate()
                }
            }

            boundsFrame(
                width = wb,
                height = wb,
                start = wb,
                gravity = Gravity.BOTTOM
            )

            addView(
                this
            )
        }

        Button(
            context
        ).apply {
            text = "-"

            setOnClickListener {
                mColorsSeek.removeLastOrNull()

                mGradientEdit.colors = IntArray(
                    mGradientEdit.colors.size - 1
                ).run {
                    for (i in indices) {
                        this[i] = mGradientEdit.colors[i]
                    }

                    this
                }

                mGradientEdit.positions = FloatArray(
                    mGradientEdit.positions.size - 1
                ).run {
                    for (i in indices) {
                        this[i] = mGradientEdit.positions[i]
                    }
                    this
                }

                mViewGradMaker?.apply {
                    setInitialValues(
                        mColorsSeek
                    )
                    invalidate()
                }
            }

            boundsFrame(
                width = wb,
                height = wb,
                gravity = Gravity.BOTTOM
            )

            addView(
                this
            )
        }

        boundsFrame(
            width = VEApp.width.toFloat(),
            height = mwplacer,
            gravity = Gravity.BOTTOM
        )
    }

    override fun onReadyGradientShader() {
        mViewGradPlacer?.apply {
            changeShader()
            confirmFill()
            invalidate()
        }
    }

    override fun onSelectColorSeek(
        index: Int
    ) {
        VEBottomSheetSelectColor(
            toView
        ) {
            it ?: return@VEBottomSheetSelectColor
            mViewGradMaker?.apply {
                updateGradientColorSeek(
                    index,
                    it.color.toInt32()
                )
                invalidate()
            }
        }.show()
    }

    override fun onChangeGradientPosition() {
        confirmFill()
    }

    private inline fun confirmFill() = mGradientEdit.run {
        onConfirmFill.onConfirmFill(
            VEMFillGradientLinear(
                from.x / mwplacer * canvasSize.width,
                from.y / mwplacer * canvasSize.height,
                to.x / mwplacer * canvasSize.width,
                to.y / mwplacer * canvasSize.height,
                colors,
                positions
            )
        )
    }

}
