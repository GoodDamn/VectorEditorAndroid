package good.damn.editor.vector

import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.animation.animator.options.VEOptionAnimatorBase
import good.damn.editor.animation.animator.options.tickTimer.listeners.VEListenerOnTickColor
import good.damn.editor.editmodes.VEEditModeAnimation
import good.damn.editor.vector.browsers.VEBrowserContent
import good.damn.editor.vector.browsers.interfaces.VEListenerOnGetBrowserContent
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.editor.editmodes.VEEditModeFreeMove
import good.damn.editor.editmodes.VEEditModeShape
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectShape
import good.damn.editor.vector.bottomsheets.VEBottomSheetColorPick
import good.damn.editor.vector.fragments.adapter.VEFragmentAdapter
import good.damn.editor.vector.fragments.VEFragmentVectorAnimation
import good.damn.editor.vector.fragments.VEFragmentVectorEdit
import good.damn.editor.views.VEViewVectorEditor
import good.damn.gradient_color_picker.OnPickColorListener
import good.damn.lib.verticalseekbar.VSViewSeekBarV
import good.damn.lib.verticalseekbar.interfaces.VSIListenerSeekBarProgress
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.VEShapeBezierС
import good.damn.sav.core.shapes.VEShapeLine
import good.damn.sav.misc.interfaces.VEIDrawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.LinkedList

class VEActivityMain
: AppCompatActivity(),
VEListenerOnGetBrowserContent,
VSIListenerSeekBarProgress,
VEIDrawable,
VEIListenerOnAnchorPoint,
OnPickColorListener,
VEListenerOnTickColor,
VEIListenerOnSelectShape {

    companion object {
        private val TAG = VEActivityMain::class.simpleName
    }

    private var mViewVector: VEViewVectorEditor? = null
    private var mViewColor: View? = null
    private var mCurrentAnchor: VEIListenerOnAnchorPoint? = null
    private var mViewPager: ViewPager2? = null

    private val mBrowserContent = VEBrowserContent().apply {
        onGetContent = this@VEActivityMain
    }

    private val mAnchor = VEAnchor(
        50f
    ).apply {
        onAnchorPoint = this@VEActivityMain
    }

    private val mFragmentVectorEdit = VEFragmentVectorEdit().apply {
        onClickDeleteAll = View.OnClickListener {
            onClickDeleteAll(it)
        }

        onClickExport = View.OnClickListener {
            onClickExportVector(it)
        }

        onClickImport = View.OnClickListener {
            onClickImportVector(it)
        }

        onClickUndoAction = View.OnClickListener {
            onClickUndoAction(it)
        }

        onClickBtnNext = View.OnClickListener {
            onClickBtnNext(it)
        }
    }

    private val mFragmentVectorAnimation = VEFragmentVectorAnimation().apply {
        onClickBtnPrev = View.OnClickListener {
            onClickBtnPrev(it)
        }

        onTickUpdateAnimation = {
            mViewVector?.invalidate()
        }
    }

    private val modeShape = VEEditModeShape(
        mAnchor,
        VEApp.width.toFloat(),
        VEApp.width.toFloat()
    ).apply {
        onSelectShape = this@VEActivityMain
    }

    private val modeFreeMove = VEEditModeFreeMove(
        mAnchor,
        modeShape.skeleton
    )

    private val modeAnimation = VEEditModeAnimation(
        mAnchor,
        modeShape.skeleton
    ).apply {
        onChangePoint = mFragmentVectorAnimation
        onChangePointPosition = mFragmentVectorAnimation
    }


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        mFragmentVectorAnimation.onPlayAnimation = {
            val list = ArrayList<VEOptionAnimatorBase>(
                modeAnimation.animatedPoints.size
            )

            for (pia in modeAnimation.animatedPoints.values) {
                pia.options.forEach {
                    list.add(it)
                }
            }

            list.toTypedArray()
        }

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

        mCurrentAnchor = modeShape

        mViewVector = VEViewVectorEditor(
            context,
            modeShape
        ).apply {

            setBackgroundColor(
                0xff565656.toInt()
            )

            boundsFrame(
                width = modeShape.canvasWidth,
                height = modeShape.canvasHeight
            )

            canvasRenderer = this@VEActivityMain

            root.addView(
                this
            )
        }


        Button(
            context
        ).apply {
            text = "SHP"

            val s = VEApp.width * 0.15f

            setOnClickListener {
                mViewVector?.mode = modeShape
                mCurrentAnchor = modeShape
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
            text = "MOV"

            val s = VEApp.width * 0.15f

            setOnClickListener {
                mViewVector?.mode = modeFreeMove
                mCurrentAnchor = modeFreeMove
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
            text = "|"

            val s = VEApp.width * 0.1f
            boundsFrame(
                width = s,
                height = s
            )

            setOnClickListener {
                modeShape.currentPrimitive = VEShapeLine(
                    0f, 0f
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
                start = s
            )

            setOnClickListener {
                modeShape.currentPrimitive = VEShapeBezierС(
                    0f, 0f
                )
            }

            root.addView(
                this
            )
        }


        VSViewSeekBarV(
            context
        ).apply {

            boundsFrame(
                gravity = Gravity.END,
                height = VEApp.height * 0.2f,
                top = VEApp.width * 0.42f,
                width = VEApp.width * 0.1f
            )

            onSeekProgress = this@VEActivityMain

            strokeWidth = layoutParams.width * 0.25f
            setBackgroundColor(
                0xffff0000.toInt()
            )
            progressColor = 0xffffff00.toInt()

            progress = 0.65f

            root.addView(
                this
            )
        }

        mViewColor = View(
            context
        ).apply {
            setOnClickListener {
                VEBottomSheetColorPick(
                    root
                ).apply {
                    onPickColor = this@VEActivityMain
                    show()
                }
            }

            setBackgroundColor(
                0xffffffff.toInt()
            )

            val size = modeShape.canvasWidth * 0.1f
            val margin = modeShape.canvasWidth * 0.01f

            boundsFrame(
                gravity = Gravity.END,
                top = modeShape.canvasHeight - size - margin,
                end = margin,
                width = size,
                height = size
            )

            root.addView(
                this
            )
        }

        mViewPager = ViewPager2(
            context
        ).apply {

            isUserInputEnabled = false

            adapter = VEFragmentAdapter(
                arrayOf(
                    mFragmentVectorEdit,
                    mFragmentVectorAnimation
                ),
                supportFragmentManager,
                lifecycle
            )

            boundsFrame(
                top = modeShape.canvasHeight,
                width = -1f,
                height = -1f
            )

            root.addView(
                this
            )
        }

        setContentView(
            root
        )
    }


    override fun onGetBrowserContent(
        uri: Uri?
    ) = Unit

    override fun onSeekProgress(
        progress: Float
    ) {
        modeShape.apply {
            currentPrimitive.strokeWidth =
                progress * canvasWidth
        }

        mViewVector?.invalidate()
    }

    override fun draw(
        canvas: Canvas
    ) = modeShape.run {
        skeleton.draw(
            canvas
        )

        shapes.forEach {
            it.draw(
                canvas
            )
            true
        }

        modeShape.draw(
            canvas
        )
    }

    override fun onAnchorX(
        x: Float
    ) {
        mCurrentAnchor?.onAnchorX(x)
    }

    override fun onAnchorY(
        y: Float
    ) {
        mCurrentAnchor?.onAnchorY(y)
    }

    private fun onClickExportVector(
        v: View
    ) = Unit

    private fun onClickImportVector(
        v: View
    ) {
        mBrowserContent.launch()
    }

    private fun onClickDeleteAll(
        v: View
    ) {
        modeShape.clearActions()
        mViewVector?.invalidate()
    }

    private fun onClickUndoAction(
        v: View
    ) {
        modeShape.undoAction()
        mViewVector?.invalidate()
    }

    private fun onClickBtnPrev(
        v: View
    ) {
        mViewPager?.currentItem = 0
        mViewVector?.mode = modeShape
        mCurrentAnchor = modeShape
    }

    private fun onClickBtnNext(
        v: View
    ) {
        mViewPager?.currentItem = 1
        mViewVector?.mode = modeAnimation
        mCurrentAnchor = modeAnimation
    }

    override fun onPickColor(
        color: Int
    ) {
        modeShape
            .currentPrimitive
            .color = color

        mViewColor?.setBackgroundColor(
            color
        )

        mViewVector?.invalidate()

        mFragmentVectorEdit.pickColor(
            color
        )

        mFragmentVectorAnimation.pickColor(
            color
        )
    }

    override fun onTickColor(
        color: Int
    ) {
        modeShape.currentPrimitive.color = color
        mViewVector?.invalidate()
    }

    override fun onSelectShape(
        shape: VEShapeBase
    ) {
        val view = window.decorView
            as? ViewGroup ?: return

        VEBottomSheetColorPick(
            view
        ).apply {
            onPickColor = OnPickColorListener {
                shape.color = it
                mViewVector?.invalidate()
            }
            show()
        }
    }
}