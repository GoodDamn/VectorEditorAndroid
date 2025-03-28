package good.damn.editor.vector

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import good.damn.editor.importer.VEAssetLoader
import good.damn.editor.importer.VEModelImport
import good.damn.editor.importer.animation.VEModelImportAnimation
import good.damn.editor.importer.VEViewAVS
import good.damn.editor.importer.animation.VEImportAnimationDefault
import good.damn.editor.vector.browsers.VEBrowserContent
import good.damn.editor.vector.browsers.interfaces.VEListenerOnGetBrowserContent
import good.damn.editor.vector.extensions.extension
import good.damn.editor.vector.importer.VEImportAnimationMutable
import good.damn.sav.core.animation.animators.VEAnimatorGlobal
import good.damn.sav.core.animation.animators.VEIListenerAnimation
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

    private var mCurrentAnimation: VEModelImportAnimation<
        VEIListenerAnimation
    >? = null

    private val mCanvasSize = Size()
    private var mViewAvs: VEViewAVS? = null

    private var mScrollLayout: LinearLayout? = null

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        val context = this
        val heightAnim = (VEApp.height * 0.25f).toInt()
        LinearLayout(
            context
        ).apply {

            orientation = LinearLayout.VERTICAL

            setBackgroundColor(
                0xff000315.toInt()
            )

            mCanvasSize.width = VEApp.width.toFloat()
            mCanvasSize.height = VEApp.width.toFloat()

            ScrollView(
                context
            ).let { scrollView ->

                scrollView.setBackgroundColor(0)

                LinearLayout(
                    context
                ).let { scrollLayout ->
                    scrollLayout.orientation = LinearLayout
                        .VERTICAL

                    mScrollLayout = scrollLayout
                    scrollLayout.setBackgroundColor(0)
                    scrollView.addView(
                        scrollLayout
                    )
                }

                addView(
                    scrollView,
                    VEApp.width,
                    heightAnim
                )
            }

            VEViewAVS(
                context
            ).apply {
                mViewAvs = this

                setBackgroundColor(0)

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
                    mCanvasSize.width.toInt(),
                    mCanvasSize.height.toInt()
                )
            }

            setContentView(
                this
            )
        }

        VEBrowserContent().apply {
            register(this@VEActivityImport)
            onGetContent = this@VEActivityImport
            launch()
        }
    }

    override fun onGetBrowserContent(
        list: List<Uri>
    ) = list.forEach { uri ->
        when (
            uri.extension
        ) {
            "avs" -> loadAssetStaticAnimation(uri)
            "avss" -> loadAssetStatic(uri)
            "avsa" -> loadAssetAnimation(uri)
        }
    }

    override suspend fun onUpdateFrameAnimation() = withContext(
        Dispatchers.Main
    ) {
        mViewAvs?.invalidate()
    } ?: Unit

    private inline fun loadAssetStaticAnimation(
        uri: Uri
    ) = VEAssetLoader.loadAssetStaticAnimation(
        mCanvasSize,
        contentResolver,
        VEImportAnimationDefault(
            mCanvasSize
        ),
        uri
    )?.let {
        mViewAvs?.model = it.first
        mCurrentAnimation = it.second.apply {
            mScrollLayout?.let { lay ->
                lay.removeAllViews()
                placeDurations(
                    lay,
                    this@VEActivityImport,
                    this
                )
            }
        }
    }

    private inline fun loadAssetStatic(
        uri: Uri
    ) = VEAssetLoader.loadAssetStatic(
        mCanvasSize,
        contentResolver,
        uri
    ).let {
        mViewAvs?.model = it
        mCurrentAnimation = null
    }

    private inline fun loadAssetAnimation(
        uri: Uri
    ) = mViewAvs?.model?.run {
        mCurrentAnimation = VEAssetLoader.loadAssetAnimation(
            shapes,
            skeleton,
            groupsFill,
            VEImportAnimationDefault(
                mCanvasSize
            ),
            contentResolver,
            uri
        )?.apply {
            mScrollLayout?.let {
                placeDurations(
                    it,
                    this@VEActivityImport,
                    this
                )
            }
        }
    }

}

private inline fun placeDurations(
    rootLayout: LinearLayout,
    context: Context,
    animation: VEModelImportAnimation<VEIListenerAnimation>
) = animation.animations?.forEach {
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

        rootLayout.addView(
            this,
            -1,
            -2
        )
    }

}