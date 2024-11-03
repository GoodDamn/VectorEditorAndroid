package good.damn.editor.vector.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import good.damn.editor.vector.interfaces.VEIColorPickable

class VEFragmentVectorEdit
: Fragment(),
    VEIColorPickable {

    var onClickDeleteAll: View.OnClickListener? = null
    var onClickUndoAction: View.OnClickListener? = null
    var onClickImport: View.OnClickListener? = null
    var onClickExport: View.OnClickListener? = null

    var onClickBtnNext: View.OnClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (context == null) {
            return null
        }

        val root = LinearLayout(
            context
        ).apply {
            orientation = LinearLayout.VERTICAL
        }

        LinearLayout(
            context
        ).apply {
            orientation = LinearLayout.HORIZONTAL

            Button(
                context
            ).apply {
                text = "Export"
                setOnClickListener(
                    onClickExport
                )
                addView(
                    this
                )
            }

            Button(
                context
            ).apply {
                text = "Import"
                setOnClickListener(
                    onClickImport
                )
                addView(this)
            }

            Button(
                context
            ).apply {
                text = "Delete all"
                setOnClickListener(
                    onClickDeleteAll
                )
                addView(this)
            }

            Button(
                context
            ).apply {
                text = "Undo"
                setOnClickListener(
                    onClickUndoAction
                )
                addView(this)
            }

            root.addView(
                this,
                -2,
                -2
            )
        }

        Button(
            context
        ).apply {

            text = ">"

            setOnClickListener(
                onClickBtnNext
            )

            root.addView(
                this,
                -2,
                -2
            )
        }

        return root
    }

    override fun pickColor(
        color: Int
    ) {}

}