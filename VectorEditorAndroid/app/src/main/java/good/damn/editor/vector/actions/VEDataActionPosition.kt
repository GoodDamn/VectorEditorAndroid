package good.damn.editor.vector.actions

import android.graphics.PointF

class VEDataActionPosition(
    private val mPointSaved: PointF
): VEIActionable {

    private val mPoint = PointF(
        mPointSaved.x,
        mPointSaved.y
    )

    override fun removeAction() {
        mPointSaved.set(
            mPoint
        )
    }

}