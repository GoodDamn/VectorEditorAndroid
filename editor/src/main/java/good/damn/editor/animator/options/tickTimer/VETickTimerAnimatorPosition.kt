package good.damn.editor.animator.options.tickTimer

import android.util.Log
import good.damn.editor.animator.options.tickTimer.base.VETickTimerAnimatorBase
import good.damn.editor.animator.options.tickTimer.data.VETickDataPosition
import good.damn.editor.animator.options.tickTimer.data.base.VETickData

class VETickTimerAnimatorPosition
: VETickTimerAnimatorBase() {

    companion object {
        private val TAG = VETickTimerAnimatorPosition::class.simpleName
    }

    var tickX = 0f
    var tickY = 0f

    override fun tick(
        tickTimeMs: Int,
        tickTimeFactor: Float
    ) {
        tickList.add(
            VETickDataPosition(
                tickTimeFactor,
                tickX,
                tickY
            )
        )
    }

    override fun interpolate(
        from: VETickData,
        to: VETickData,
        t: Float
    ) {
        from as VETickDataPosition
        to as VETickDataPosition

        //val x = from.x + (to.x - from.x) * t
        //val y = from.y + (to.y - from.y) * t
        Log.d(TAG, "interpolate: $t")
    }

    override fun beginTickData() = VETickDataPosition(
        0.0f,
        0f,
        0f
    )
}