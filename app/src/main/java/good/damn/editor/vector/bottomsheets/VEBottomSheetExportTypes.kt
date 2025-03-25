package good.damn.editor.vector.bottomsheets

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import good.damn.editor.vector.VEApp
import good.damn.editor.vector.extensions.views.bounds
import good.damn.editor.vector.extensions.views.boundsFrame

class VEBottomSheetExportTypes(
    toView: ViewGroup
): VEBottomSheet(
    toView
) {

    var onClickAvs: View.OnClickListener? = null
    var onClickAvss: View.OnClickListener? = null
    var onClickAvsa: View.OnClickListener? = null

    override fun onCreateView(
        context: Context
    ) = LinearLayout(
        context
    ).apply {

        orientation = LinearLayout.VERTICAL

        val height = VEApp.height * 0.25f

        setBackgroundColor(
            0xff000315.toInt()
        )

        AppCompatTextView(
            context
        ).apply {

            setOnClickListener(
                onClickAvs
            )

            text = ".avs (Static animation)"

            gravity = Gravity.CENTER

            setTextColor(-1)

            bounds(
                -1f,
                height * 0.3f
            )

            addView(
                this
            )
        }

        AppCompatTextView(
            context
        ).apply {

            text = ".avss (Static)"

            setOnClickListener(
                onClickAvss
            )

            gravity = Gravity.CENTER

            setTextColor(-1)

            bounds(
                -1f,
                height * 0.3f
            )

            addView(
                this
            )
        }

        AppCompatTextView(
            context
        ).apply {

            text = ".avsa (Animation)"

            setOnClickListener(
                onClickAvsa
            )

            gravity = Gravity.CENTER

            setTextColor(-1)

            bounds(
                -1f,
                height * 0.3f
            )

            addView(
                this
            )
        }


        boundsFrame(
            width = VEApp.width.toFloat(),
            height = height,
            gravity = Gravity.BOTTOM
        )
    }
}