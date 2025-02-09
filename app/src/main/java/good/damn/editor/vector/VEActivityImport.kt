package good.damn.editor.vector

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import good.damn.editor.importer.VEModelImport
import good.damn.editor.importer.VEModelImportAnimation
import good.damn.editor.importer.VEViewAVS
import good.damn.editor.vector.browsers.VEBrowserContent
import good.damn.editor.vector.browsers.interfaces.VEListenerOnGetBrowserContent
import good.damn.sav.core.animation.animators.VEAnimatorGlobal
import good.damn.sav.core.animation.animators.VEIListenerAnimationUpdateFrame
import good.damn.sav.misc.Size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VEActivityImport
: AppCompatActivity(),
VEListenerOnGetBrowserContent,
VEIListenerAnimationUpdateFrame {

    private val mAnimator = VEAnimatorGlobal().apply {
        onUpdateFrameAnimation = this@VEActivityImport
    }

    private var mCurrentAnimation: VEModelImportAnimation? = null
    private val mCanvasSize = Size()
    private var mViewAvs: VEViewAVS? = null

    private var mRootLayout: FrameLayout? = null

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

            mRootLayout = this

            setBackgroundColor(
                0xff000315.toInt()
            )

            mCanvasSize.width = VEApp.width.toFloat()
            mCanvasSize.height = VEApp.width.toFloat()

            VEViewAVS(
                context
            ).apply {
                mViewAvs = this

                setOnClickListener {

                    if (mAnimator.isPlaying) {
                        mAnimator.stop()
                    }

                    mCurrentAnimation?.animations?.apply {
                        mAnimator.play(
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

        VEBrowserContent().apply {
            register(this@VEActivityImport)
            onGetContent = this@VEActivityImport
            launch("*/*")
        }
    }

    override fun onGetBrowserContent(
        uri: Uri?
    ) {
        uri ?: return

        val inp = contentResolver.openInputStream(
            uri
        ) ?: return

        mCurrentAnimation = VEModelImport.createAnimationFromStream(
            inp,
            mCanvasSize,
            false
        )?.apply {
            inp.close()

            mViewAvs?.model = static

            mRootLayout?.let {
                if (it.childCount > 1) {
                    it.removeViewAt(0)
                }

                placeDurations(
                    mRootLayout!!,
                    this@VEActivityImport,
                    this@apply
                )
            }
        }
    }

    override suspend fun onUpdateFrameAnimation() = withContext(
        Dispatchers.Main
    ) {
        mViewAvs?.invalidate()
    } ?: Unit

}

private inline fun placeDurations(
    rootLayout: ViewGroup,
    context: Context,
    animation: VEModelImportAnimation
) = LinearLayout(
    context
).apply {
    orientation = LinearLayout.HORIZONTAL

    animation.animations.forEach {
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

            addView(
                this,
                -2,
                -2
            )
        }

    }

    rootLayout.addView(
        this,
        -1,
        -2
    )
}