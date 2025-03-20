package good.damn.editor.vector.bottomsheets

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.extensions.views.boundsFrame

abstract class VEBottomSheet(
    private val mViewGroup: ViewGroup
) {

    private var mView: View? = null

    fun show() {
        mView = createView(
            mViewGroup.context
        )

        mViewGroup.addView(
            mView!!
        )
    }

    open fun dismiss() {
        mView?.apply {
            mViewGroup.removeView(
                this
            )
        }
    }

    private fun createView(
        context: Context
    ) = FrameLayout(
        context
    ).apply {

        setBackgroundColor(
            0x15000000
        )

        setOnClickListener {
            dismiss()
        }

        addView(
            onCreateView(
                context
            ).apply {
                isClickable = true
                if (layoutParams != null) {
                    return@apply
                }

                boundsFrame(
                    top = VEApp.height * 0.7f,
                    width = -1f,
                    height = -1f
                )
            }
        )
    }

    abstract fun onCreateView(
        context: Context
    ): View
}