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
import androidx.viewpager2.widget.ViewPager2
import good.damn.editor.anchors.VEAnchor
import good.damn.editor.anchors.listeners.VEIListenerOnAnchorPoint
import good.damn.editor.animation.animator.options.VEOptionAnimatorBase
import good.damn.editor.animation.animator.options.tickTimer.listeners.VEListenerOnTickColor
import good.damn.editor.editmodes.animation.VEEditModeAnimation
import good.damn.editor.vector.browsers.VEBrowserContent
import good.damn.editor.vector.browsers.interfaces.VEListenerOnGetBrowserContent
import good.damn.editor.vector.extensions.views.boundsFrame
import good.damn.editor.editmodes.VEEditModeShape
import good.damn.editor.editmodes.VEEditModeSwap
import good.damn.editor.editmodes.freemove.VEEditModeExistingPoint
import good.damn.editor.editmodes.freemove.VEEditModeExistingShape
import good.damn.editor.editmodes.listeners.VEIListenerOnSelectShape
import good.damn.editor.vector.bottomsheets.VEBottomSheetSetupShape
import good.damn.editor.vector.fragments.adapter.VEFragmentAdapter
import good.damn.editor.vector.fragments.VEFragmentVectorAnimation
import good.damn.editor.vector.fragments.VEFragmentVectorEdit
import good.damn.editor.views.VEViewVectorEditor
import good.damn.gradient_color_picker.OnPickColorListener
import good.damn.lib.verticalseekbar.interfaces.VSIListenerSeekBarProgress
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.VEShapeBezierС
import good.damn.sav.core.shapes.VEShapeLine
import good.damn.sav.misc.interfaces.VEIDrawable

class VEActivityMain
: AppCompatActivity(),
VEListenerOnGetBrowserContent,
VEIDrawable,
VEIListenerOnAnchorPoint,
VEListenerOnTickColor,
VEIListenerOnSelectShape {

    companion object {
        private val TAG = VEActivityMain::class.simpleName
    }

    private var mViewVector: VEViewVectorEditor? = null
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

    private val modeExistingPoint = VEEditModeExistingPoint(
        modeShape.skeleton,
        mAnchor
    )

    private val modeFreeMove = VEEditModeSwap(
        arrayOf(
            modeExistingPoint,
            VEEditModeExistingShape(
                modeShape.shapes
            ).apply {
                onSelectShape = this@VEActivityMain
            }
        )
    )

    private val modeAnimation = VEEditModeAnimation(
        mAnchor,
        modeShape.skeleton,
        modeShape.shapes
    ).apply {
        onChangeEntityAnimation = mFragmentVectorAnimation
        onChangeValueAnimation = mFragmentVectorAnimation
        editModeAnimShape.onSelectShape = this@VEActivityMain
    }


    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        mFragmentVectorAnimation.onPlayAnimation = {
            modeAnimation.animatedEntities.run {
                val list = ArrayList<VEOptionAnimatorBase>(size)
                for (pia in values) {
                    pia.options.forEach {
                        list.add(it)
                    }
                }

                list.toTypedArray()
            }
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
                mCurrentAnchor = modeExistingPoint
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
        mCurrentAnchor = modeAnimation.anchor
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

        VEBottomSheetSetupShape(
            view
        ).apply {
            onSeekProgressWidth = object: VSIListenerSeekBarProgress {
                override fun onSeekProgress(
                    progress: Float
                ) {
                    shape.strokeWidth = progress * modeShape.canvasWidth
                    mViewVector?.invalidate()
                }
            }
            onPickColor = OnPickColorListener {
                shape.color = it
                modeAnimation.editModeAnimShape.changeShapeColor(
                    it
                )
                mViewVector?.invalidate()
            }
            show()
        }
    }
}