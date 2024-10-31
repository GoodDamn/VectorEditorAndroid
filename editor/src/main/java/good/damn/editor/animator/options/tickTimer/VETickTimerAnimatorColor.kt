package good.damn.editor.animator.options.tickTimer

import android.util.Log
import androidx.annotation.ColorInt
import good.damn.editor.animator.options.tickTimer.base.VETickTimerAnimatorBase
import good.damn.editor.animator.options.tickTimer.data.VETickDataColor
import good.damn.editor.animator.options.tickTimer.data.base.VETickData

class VETickTimerAnimatorColor
: VETickTimerAnimatorBase() {

    companion object {
        private val TAG = VETickTimerAnimatorColor::class.simpleName
    }

    @setparam:ColorInt
    @get:ColorInt
    var color: Int = 0

    override fun tick(
        tickTimeMs: Int,
        tickTimeFactor: Float
    ) {
        tickList.add(
            VETickDataColor(
                tickTimeFactor,
                color
            )
        )
    }

    override fun interpolate(
        from: VETickData,
        to: VETickData,
        t: Float
    ) {
        from as VETickDataColor
        to as VETickDataColor

        Log.d(TAG, "interpolate: $t")
    }
    
    override fun beginTickData() = VETickDataColor(
        0.0f,
        0
    )

}