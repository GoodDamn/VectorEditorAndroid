package good.damn.editor.importer.animation

import android.graphics.PointF
import good.damn.editor.importer.animation.extractor.VEIImportAnimationExtractor
import good.damn.editor.importer.animation.extractor.VEImportAnimationExtractorFill
import good.damn.editor.importer.animation.extractor.VEImportAnimationExtractorPosition
import good.damn.editor.importer.animation.extractor.VEImportAnimationExtractorStrokeWidth
import good.damn.sav.core.animation.animators.VEAnimatorGlobal
import good.damn.sav.core.animation.animators.VEAnimatorInterpolation
import good.damn.sav.core.animation.animators.VEIListenerAnimation
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.misc.Size
import java.io.InputStream

class VEImportAnimationDefault(
    private val canvasSize: Size
): VEIListenerImportAnimation<VEIListenerAnimation> {

    override fun createShapeAnimation(
        property: Int,
        keyframesCount: Int,
        shape: VEShapeBase,
        inp: InputStream
    ) = VEAnimatorInterpolation(
        VEIImportAnimationExtractor.extractAnimation(
            keyframesCount,
            inp,
            when (property) {
                0 -> VEImportAnimationExtractorFill(
                    shape,
                    canvasSize
                )
                else -> VEImportAnimationExtractorStrokeWidth(
                    shape,
                    canvasSize
                )
            }
        )
    )

    override fun createPointAnimation(
        property: Int,
        keyframesCount: Int,
        point: PointF,
        inp: InputStream
    ) = VEAnimatorInterpolation(
        VEIImportAnimationExtractor.extractAnimation(
            keyframesCount,
            inp,
            VEImportAnimationExtractorPosition(
                canvasSize,
                point
            )
        )
    )
}