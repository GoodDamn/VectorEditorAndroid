package good.damn.editor.importer.animation.extractor

import good.damn.sav.core.animation.interpolators.VEAnimationInterpolatorStrokeWidth
import good.damn.sav.core.animation.interpolators.fill.VEAnimationInterpolatorFill
import good.damn.sav.core.animation.keyframe.VEMKeyframeStrokeWidth
import good.damn.sav.core.animation.keyframe.fill.VEMKeyframeFill
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.misc.Size
import java.io.InputStream

class VEImportAnimationExtractorStrokeWidth(
    var shape: VEShapeBase,
    private val canvasSize: Size
): VEIImportAnimationExtractor<
    VEMKeyframeStrokeWidth,
    VEAnimationInterpolatorStrokeWidth
> {

    override fun createKeyframe(
        stream: InputStream,
        factor: Float
    ) = VEMKeyframeStrokeWidth.import(
        canvasSize,
        factor,
        stream
    )

    override fun createInterpolator(
        start: VEMKeyframeStrokeWidth,
        end: VEMKeyframeStrokeWidth
    ) = VEAnimationInterpolatorStrokeWidth(
        start,
        end,
        shape
    )


}