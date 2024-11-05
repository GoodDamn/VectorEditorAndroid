package good.damn.editor.animation.animators

import good.damn.editor.animation.animator.options.VEOptionAnimatorBase
import good.damn.editor.animation.animator.options.tickTimer.data.base.VETickData
import good.damn.editor.interfaces.VEIInterpolatable
import good.damn.sav.misc.structures.tree.toList

class VEAnimatorTick {

    private lateinit var mOptionsPrepared: Array<Tick?>

    fun prepare(
        options: Array<VEOptionAnimatorBase>
    ) {
        mOptionsPrepared = Array(
            options.size
        ) {
            val tickTimer = options[it]
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
    }

    fun tick(
        dt: Float
    ) = mOptionsPrepared.forEach { it?.apply {
        currentPathTime += dt
        if (currentPathTime > to.tickFactor) {
            from = to
            if (!ticks.hasNext()) {
                return@forEach
            }
            to = ticks.next()
            dtPath = to.tickFactor - from.tickFactor
            return@forEach
        }

        val i = (currentPathTime - from.tickFactor) / dtPath

        interpolation.interpolate(
            from,
            to,
            i
        )
    }
    }


    private data class Tick(
        val ticks: Iterator<VETickData>,
        val interpolation: VEIInterpolatable,
        var from: VETickData,
        var to: VETickData,
        var currentPathTime: Float = 0f,
        var dtPath: Float = to.tickFactor - from.tickFactor
    )
}