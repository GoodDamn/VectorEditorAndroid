package good.damn.editor.vector

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.importer.VEModelImport
import good.damn.editor.importer.VEViewAVS
import good.damn.sav.core.animation.animators.VEAnimatorGlobal
import good.damn.sav.core.animation.animators.VEIListenerAnimationUpdateFrame
import good.damn.sav.misc.Size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VEActivityImport
: AppCompatActivity() {

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        val context = this

        FrameLayout(
            context
        ).apply {

            setBackgroundColor(
                0xff000315.toInt()
            )

            val canvasSize = Size(
                VEApp.width.toFloat(),
                VEApp.width.toFloat()
            )

            val animation = VEModelImport.createAnimationFromResource(
                resources,
                R.raw.anim,
                canvasSize
            )

            val animator = VEAnimatorGlobal()

            VEViewAVS(
                context
            ).apply {

                model = animation?.static

                animator.onUpdateFrameAnimation = VEIListenerAnimationUpdateFrame {
                    withContext(
                        Dispatchers.Main
                    ) {
                        invalidate()
                    }
                }

                setOnClickListener {

                    if (animator.isPlaying) {
                        animator.stop()
                    }

                    animation?.animations?.apply {
                        animator.play(
                            0L,
                            this
                        )
                    }
                }

                addView(
                    this,
                    VEApp.width,
                    VEApp.width
                )
            }

            LinearLayout(
                context
            ).let { lay ->

                lay.orientation = LinearLayout.HORIZONTAL

                animation?.animations?.forEach {
                    EditText(
                        context
                    ).apply {
                        setText(
                            it.duration.toString()
                        )

                        setTextColor(
                            0xafafafaf.toInt()
                        )

                        addTextChangedListener(
                            object: TextWatcher {
                                override fun beforeTextChanged(
                                    s: CharSequence?,
                                    start: Int,
                                    count: Int,
                                    after: Int
                                ) = Unit

                                override fun onTextChanged(
                                    s: CharSequence?,
                                    start: Int,
                                    before: Int,
                                    count: Int
                                ) {
                                    it.duration = s?.toString()?.toIntOrNull() ?: 1000
                                }

                                override fun afterTextChanged(s: Editable?) = Unit

                            }
                        )

                        lay.addView(
                            this,
                            -2,
                            -2
                        )
                    }

                }

                addView(
                    lay,
                    -1,
                    -2
                )
            }

            setContentView(
                this
            )
        }

    }

}