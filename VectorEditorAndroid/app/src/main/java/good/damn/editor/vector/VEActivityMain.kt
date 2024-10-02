package good.damn.editor.vector

import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import good.damn.editor.vector.browsers.VEBrowserContent
import good.damn.editor.vector.browsers.interfaces.VEListenerOnGetBrowserContent
import good.damn.editor.vector.porters.VEExporter
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.editor.vector.files.VEFileDocument
import good.damn.editor.vector.options.VEOptionFreeMove
import good.damn.editor.vector.options.VEOptionHookPointer
import good.damn.editor.vector.options.VEOptionPrimitivable
import good.damn.editor.vector.paints.VEPaintBezierС
import good.damn.editor.vector.paints.VEPaintLine
import good.damn.editor.vector.porters.VEImporter
import good.damn.editor.vector.views.VEViewVector
import good.damn.gradient_color_picker.GradientColorPicker

class VEActivityMain
: AppCompatActivity(),
VEListenerOnGetBrowserContent {

    private var mViewVector: VEViewVector? = null

    private val mExporter = VEExporter()
    private val mImporter = VEImporter()

    private val mBrowserContent = VEBrowserContent().apply {
        onGetContent = this@VEActivityMain
    }

    private val mOptionPrimitive = VEOptionPrimitivable(
        VEApp.height * 0.3f
    )
    private val mOptionHook = VEOptionHookPointer()
    private val mOptionFreeMove = VEOptionFreeMove()

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        mBrowserContent.register(
            this
        )

        val context = this
        val root = FrameLayout(
            context
        ).apply {
            setBackgroundColor(
                0xff08193A.toInt()
            )
        }

        mViewVector = VEViewVector(
            context,
            mOptionPrimitive
        ).apply {

            setBackgroundColor(
                0xff565656.toInt()
            )

            isAlignedVertical = true
            isAlignedHorizontal = true

            boundsFrame(
                width = mOptionPrimitive.canvasWidth,
                height = mOptionPrimitive.canvasHeight
            )

            root.addView(
                this
            )
        }


        Button(
            context
        ).apply {
            text = "SER"

            val s = VEApp.width * 0.15f

            setOnClickListener {
                mViewVector?.optionable = mOptionPrimitive
            }

            boundsFrame(
                gravity = Gravity.END,
                width = s,
            )

            root.addView(
                this
            )
        }

        Button(
            context
        ).apply {
            text = "HOK"

            val s = VEApp.width * 0.15f

            setOnClickListener {
                mViewVector?.optionable = mOptionHook
            }

            boundsFrame(
                gravity = Gravity.END,
                width = s,
                top = s
            )

            root.addView(
                this
            )
        }

        Button(
            context
        ).apply {
            text = "MOV"

            val s = VEApp.width * 0.15f

            setOnClickListener {
                mViewVector?.optionable = mOptionFreeMove
            }

            boundsFrame(
                gravity = Gravity.END,
                width = s,
                top = 2*s
            )

            root.addView(
                this
            )
        }

        Button(
            context
        ).apply {
            text = "|"

            val s = VEApp.width * 0.1f
            boundsFrame(
                width = s,
                height = s,
                top = mOptionPrimitive.canvasHeight
            )

            setOnClickListener {
                mOptionPrimitive.currentPrimitive = VEPaintLine(
                    0f,0f
                )
            }

            root.addView(
                this
            )
        }

        Button(
            context
        ).apply {
            text = "CB"

            val s = VEApp.width * 0.1f
            boundsFrame(
                width = s * 1.5f,
                height = s,
                top = mOptionPrimitive.canvasHeight,
                start = s
            )

            setOnClickListener {
                mOptionPrimitive.currentPrimitive = VEPaintBezierС(
                    0f,0f
                )
            }

            root.addView(
                this
            )
        }

        Button(
            context
        ).apply {

            text = "H"
            val s = VEApp.width * 0.1f
            boundsFrame(
                gravity = Gravity.END,
                width = s,
                height = s,
                top = mOptionPrimitive.canvasHeight,
                end = 0f
            )

            setOnClickListener {
                mViewVector?.apply {
                    isAlignedHorizontal = !isAlignedHorizontal
                }
            }

            root.addView(
                this
            )
        }

        Button(
            context
        ).apply {

            text = "V"

            val s = VEApp.width * 0.1f
            boundsFrame(
                gravity = Gravity.END,
                width = s,
                height = s,
                top = mOptionPrimitive.canvasHeight,
                end = s
            )

            setOnClickListener {
                mViewVector?.apply {
                    isAlignedVertical = !isAlignedVertical
                }
            }

            root.addView(
                this
            )
        }



        SeekBar(
            context
        ).apply {
            progress = 0
            max = 100

            setOnSeekBarChangeListener(
                object : OnSeekBarChangeListener {
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        val n = progress / 100f
                        mOptionPrimitive
                            .currentPrimitive
                            .strokeWidth = n * VEApp.width
                    }
                    override fun onStartTrackingTouch(
                        seekBar: SeekBar?
                    ) = Unit
                    override fun onStopTrackingTouch(
                        seekBar: SeekBar?
                    ) = Unit
                }
            )

            boundsFrame(
                top = VEApp.height * 0.4f,
                width = VEApp.width.toFloat()
            )

            root.addView(
                this
            )
        }

        GradientColorPicker(
            context
        ).apply {

            boundsFrame(
                top = VEApp.height * 0.5f,
                width = VEApp.width.toFloat(),
                height = VEApp.height * 0.2f
            )

            setOnPickColorListener {
                mOptionPrimitive
                    .currentPrimitive
                    .color = it
            }

            root.addView(
                this
            )
        }

        LinearLayout(
            context
        ).apply {
            orientation = LinearLayout.HORIZONTAL

            AppCompatButton(
                context
            ).apply {
                text = "Export"
                setOnClickListener(
                    this@VEActivityMain::onClickExportVector
                )
                addView(
                    this
                )
            }

            AppCompatButton(
                context
            ).apply {
                text = "Import"
                setOnClickListener(
                    this@VEActivityMain::onClickImportVector
                )
                addView(this)
            }

            AppCompatButton(
                context
            ).apply {
                text = "Delete all"
                setOnClickListener(
                    this@VEActivityMain::onClickDeleteAll
                )
                addView(this)
            }

            AppCompatButton(
                context
            ).apply {
                text = "Undo"
                setOnClickListener(
                    this@VEActivityMain::onClickUndoAction
                )
                addView(this)
            }

            boundsFrame(
                top = VEApp.height * 0.7f
            )

            root.addView(this)
        }

        setContentView(
            root
        )

    }

    private fun onClickExportVector(
        v: View
    ) {
        /*val data = mViewVector?.primitives
            ?: return

        mExporter.exportTo(
            VEFileDocument(
                "myVector.sav"
            ),
            data
        )*/
    }

    private fun onClickImportVector(
        v: View
    ) {
        mBrowserContent.launch()
    }

    private fun onClickDeleteAll(
        v: View
    ) {
        mViewVector?.clearVector()
    }

    private fun onClickUndoAction(
        v: View
    ) {
        mViewVector?.undoVector()
    }

    override fun onGetBrowserContent(
        uri: Uri?
    ) {
        if (uri == null) {
            return
        }

        val vectorCanvas = mViewVector
            ?: return

        val stream = contentResolver.openInputStream(
            uri
        ) ?: return

        mImporter.importFrom(
            stream,
            vectorCanvas.width.toFloat(),
            vectorCanvas.height.toFloat()
        )?.let {
            vectorCanvas.invalidate()
        }
    }

}