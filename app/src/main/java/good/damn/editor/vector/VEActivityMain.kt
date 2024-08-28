package good.damn.editor.vector

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.vector.enums.VEEnumPrimitives
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.editor.vector.views.VEViewVector

class VEActivityMain
: AppCompatActivity() {

    private var mViewVector: VEViewVector? = null

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        val context = this
        val root = FrameLayout(
            context
        ).apply {
            setBackgroundColor(
                0xff08193A.toInt()
            )
        }

        val topOptions = VEApp.height * 0.3f

        mViewVector = VEViewVector(
            context
        ).apply {

            setBackgroundColor(
                0xff565656.toInt()
            )

            boundsFrame(
                width = VEApp.width.toFloat(),
                height = topOptions
            )

            root.addView(
                this
            )
        }


        View(
            context
        ).apply {

            setBackgroundColor(
                0xffff00ff.toInt()
            )

            val s = VEApp.width * 0.1f
            boundsFrame(
                width = s,
                height = s,
                top = topOptions
            )

            setOnClickListener {
                mViewVector?.currentPrimitive = VEEnumPrimitives
                    .LINE
            }

            root.addView(
                this
            )
        }

        View(
            context
        ).apply {

            setBackgroundColor(
                0xffffff00.toInt()
            )

            val s = VEApp.width * 0.1f
            boundsFrame(
                width = s,
                height = s,
                top = topOptions,
                start = s
            )

            setOnClickListener {
                mViewVector?.currentPrimitive = VEEnumPrimitives
                    .CIRCLE
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
                        mViewVector?.strokeWidth = n * VEApp.width
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

        setContentView(
            root
        )

    }

}