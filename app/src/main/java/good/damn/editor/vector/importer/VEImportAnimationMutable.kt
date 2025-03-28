package good.damn.editor.vector.importer

import good.damn.editor.animation.animator.options.canvas.VEAnimationOptionCanvasFill
import good.damn.editor.animation.animator.options.canvas.VEAnimationOptionCanvasPosition
import good.damn.editor.animation.animator.options.canvas.VEAnimationOptionCanvasWidth
import good.damn.editor.importer.animation.VEIListenerImportAnimation
import good.damn.editor.importer.animation.extractor.VEIImportAnimationExtractor
import good.damn.editor.importer.animation.extractor.VEImportAnimationExtractorFill
import good.damn.editor.importer.animation.extractor.VEImportAnimationExtractorPosition
import good.damn.editor.importer.animation.extractor.VEImportAnimationExtractorStrokeWidth
import good.damn.editor.transaction.VEIRequesterFloat
import good.damn.sav.core.animation.interpolators.fill.VEFillGroupObserver
import good.damn.sav.core.animation.keyframe.VEMAnimationOptionFill
import good.damn.sav.core.animation.keyframe.VEMAnimationOptionPosition
import good.damn.sav.core.animation.keyframe.VEMAnimationOptionWidth
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.misc.Size
import java.io.InputStream

class VEImportAnimationMutable(
    private val canvasSize: Size,
    private val requester: VEIRequesterFloat,
    private val duration: Int
): VEIListenerImportAnimation<VEMImportAnimationMutable> {

    override fun createFillAnimation(
        type: Int,
        keyframesCount: Int,
        observerFill: VEFillGroupObserver,
        inp: InputStream
    ) = VEMImportAnimationMutable(
        observerFill.id.id,
        VEAnimationOptionCanvasFill(
            observerFill,
            VEMAnimationOptionFill(
                VEIImportAnimationExtractor.extractAnimationKeyframes(
                    keyframesCount,
                    inp,
                    VEImportAnimationExtractorFill(
                        observerFill,
                        canvasSize
                    )
                ),
                duration
            ),
            requester
        )
    )

    override fun createShapeAnimation(
        property: Int,
        keyframesCount: Int,
        shape: VEShapeBase,
        inp: InputStream
    ) = VEMImportAnimationMutable(
        shape.hashCode(),
        VEAnimationOptionCanvasWidth(
            shape,
            VEMAnimationOptionWidth(
                VEIImportAnimationExtractor.extractAnimationKeyframes(
                    keyframesCount,
                    inp,
                    VEImportAnimationExtractorStrokeWidth(
                        shape,
                        canvasSize
                    )
                ),
                duration
            ),
            requester
        )
    )

    override fun createPointAnimation(
        property: Int,
        keyframesCount: Int,
        point: VEPointIndexed,
        inp: InputStream
    ) = VEMImportAnimationMutable(
        point.id.id,
        VEAnimationOptionCanvasPosition(
            point,
            VEMAnimationOptionPosition(
                VEIImportAnimationExtractor.extractAnimationKeyframes(
                    keyframesCount,
                    inp,
                    VEImportAnimationExtractorPosition(
                        canvasSize,
                        point
                    )
                ),
                duration
            ),
            requester
        )
    )
}