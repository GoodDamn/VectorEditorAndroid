package good.damn.editor.importer.animation

import android.graphics.PointF
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase
import java.io.InputStream

interface VEIListenerImportAnimation<T> {

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