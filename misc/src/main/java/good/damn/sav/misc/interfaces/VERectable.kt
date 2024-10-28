package good.damn.sav.misc.interfaces

import android.graphics.Canvas
import android.graphics.RectF

open class VERectable: VEILayoutable {

    var x: Float
        get() = mRect.left
        set(v) {
            mRect.left = v
        }

    var y: Float
        get() = mRect.top
        set(v) {
            mRect.top = v
        }

    var width: Float
        get() = mRect.width()
        set(v) {
            mRect.right = mRect.left + v
        }

    var height: Float
        get() = mRect.height()
        set(v) {
            mRect.bottom = mRect.top + v
        }

    protected val mRect = RectF()

    override fun layout(
        width: Float,
        height: Float
    ) {
        this.width = width
        this.height = height
    }
}