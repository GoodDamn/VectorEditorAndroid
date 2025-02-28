package good.damn.editor.importer.animation

import good.damn.sav.core.animation.interpolators.fill.VEAnimationObserverFill
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase
import java.io.InputStream

interface VEIListenerImportAnimation<T> {

    fun createFillAnimation(
        type: Int,
        keyframesCount: Int,
        observerFill: VEAnimationObserverFill,
        inp: InputStream
    ): T

    fun createShapeAnimation(
        property: Int,
        keyframesCount: Int,
        shape: VEShapeBase,
        inp: InputStream
    ): T

    fun createPointAnimation(
        property: Int,
        keyframesCount: Int,
        point: VEPointIndexed,
        inp: InputStream
    ): T


}