package good.damn.editor.importer.animation.extractor

import android.graphics.PointF
import good.damn.sav.core.animation.interpolators.VEAnimationInterpolatorPosition
import good.damn.sav.core.animation.keyframe.VEMKeyframePosition
import good.damn.sav.misc.Size
import java.io.InputStream

class VEImportAnimationExtractorPosition(
    private val canvasSize: Size,
    private val point: PointF
): VEIImportAnimationExtractor<
    VEMKeyframePosition,
    VEAnimationInterpolatorPosition
> {

    override fun createKeyframe(
        stream: InputStream,
        factor: Float
    ) = VEMKeyframePosition.import(
        canvasSize,
        factor,
        stream
    )

    override fun createInterpolator(
        start: VEMKeyframePosition,
        end: VEMKeyframePosition
    ) = VEAnimationInterpolatorPosition(
        start,
        end,
        point
    )


}