package good.damn.editor.importer.animation.extractor

import good.damn.sav.core.animation.interpolators.VEAnimationInterpolatorPosition
import good.damn.sav.core.animation.interpolators.fill.VEAnimationInterpolatorFill
import good.damn.sav.core.animation.keyframe.VEMKeyframePosition
import good.damn.sav.core.animation.keyframe.fill.VEMKeyframeFill
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.misc.Size
import java.io.InputStream

class VEImportAnimationExtractorFill(
    var shape: VEShapeBase,
    private val canvasSize: Size
): VEIImportAnimationExtractor<
    VEMKeyframeFill,
    VEAnimationInterpolatorFill
> {

    override fun createKeyframe(
        stream: InputStream,
        factor: Float
    ) = VEMKeyframeFill.import(
        factor,
        stream,
        canvasSize
    )

    override fun createInterpolator(
        start: VEMKeyframeFill,
        end: VEMKeyframeFill
    ) = VEAnimationInterpolatorFill(
        start,
        end,
        shape
    )


}