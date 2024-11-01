package good.damn.editor.animators

import good.damn.editor.animator.options.VEOptionAnimatorBase
import good.damn.editor.animator.options.tickTimer.data.base.VETickData
import good.damn.editor.interfaces.VEIInterpolatable
import good.damn.editor.views.VEViewAnimatorEditor
import good.damn.sav.misc.structures.tree.toList

class VEAnimatorVector(
    private val mOptions: Array<VEOptionAnimatorBase>
) {

    private val mOptionsPrepared: Array<Tick?> = Array(
        mOptions.size
    ) {
        val tickTimer = mOptions[it]
            .tickTimer

        val iterator = tickTimer
            .tickList
            .toList()
            .iterator()

        if (!iterator.hasNext()) {
            return@Array null
        }

        Tick(
            iterator,
            tickTimer,
            tickTimer.beginTickData(),
            iterator.next()
        )
    }

    fun tick(
        dt: Float
    ) {
        mOptionsPrepared.forEach { it?.apply {
            interpolationPath += dt
            if (interpolationPath > to.tickFactor) {
                from = to
                if (!ticks.hasNext()) {
                    return@forEach
                }
                to = ticks.next()
                dtPath = to.tickFactor - from.tickFactor
                return@forEach
            }

            val i = (interpolationPath - from.tickFactor) / dtPath
            VEViewAnimatorEditor.interpolation = interpolationPath
            VEViewAnimatorEditor.subInterpolation = i

            interpolation.interpolate(
                from,
                to,
                i
            )
            }
        }
    }


    private data class Tick(
        val ticks: Iterator<VETickData>,
        val interpolation: VEIInterpolatable,
        var from: VETickData,
        var to: VETickData,
        var interpolationPath: Float = 0f,
        var dtPath: Float = to.tickFactor - from.tickFactor
    )
}