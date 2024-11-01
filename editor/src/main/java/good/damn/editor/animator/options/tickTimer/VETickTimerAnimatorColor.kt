package good.damn.editor.animator.options.tickTimer

import android.util.Log
import androidx.annotation.ColorInt
import good.damn.editor.animator.options.tickTimer.base.VETickTimerAnimatorBase
import good.damn.editor.animator.options.tickTimer.data.VETickDataColor
import good.damn.editor.animator.options.tickTimer.data.base.VETickData
import good.damn.editor.animator.options.tickTimer.listeners.VEListenerOnTickColor
import good.damn.sav.misc.extensions.interpolate
import good.damn.sav.misc.extensions.primitives.toByteArray
import java.io.ByteArrayInputStream

class VETickTimerAnimatorColor
: VETickTimerAnimatorBase() {

    companion object {
        private val TAG = VETickTimerAnimatorColor::class.simpleName
    }

    @setparam:ColorInt
    @get:ColorInt
    var color: Int = 0

    var onTickColor: VEListenerOnTickColor? = null

    private val mInterpolatedColor = ByteArray(4)

    override fun tick(
        tickTimeMs: Int,
        tickTimeFactor: Float
    ) {
        tickList.add(
            VETickDataColor(
                tickTimeFactor,
                color.toByteArray()
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

        mInterpolatedColor.interpolate(
            from.argb,
            to.argb,
            t
        )

        onTickColor?.onTickColor(
            mInterpolatedColor.
        )

        Log.d(TAG, "interpolate: $t ${
            mInterpolatedColor.contentToString()
        }")
    }
    
    override fun beginTickData() = VETickDataColor(
        0.0f,
        byteArrayOf(0, 0, 0, 0)
    )

}