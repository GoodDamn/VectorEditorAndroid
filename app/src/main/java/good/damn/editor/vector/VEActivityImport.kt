package good.damn.editor.vector

import android.os.Bundle
import android.widget.FrameLayout
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

                animator.onUpdateFrameAnimation = object: VEIListenerAnimationUpdateFrame {
                    override suspend fun onUpdateFrameAnimation() {
                        withContext(
                            Dispatchers.Main
                        ) {
                            invalidate()
                        }
                    }
                }

                setOnClickListener {
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

            setContentView(
                this
            )
        }

    }

}