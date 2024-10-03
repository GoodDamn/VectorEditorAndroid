package good.damn.editor.vector.porters

import good.damn.editor.vector.extensions.fillPointsWithSkeleton
import good.damn.editor.vector.extensions.io.readFraction
import good.damn.editor.vector.extensions.io.readU
import good.damn.editor.vector.lists.VEListShapes
import good.damn.editor.vector.models.VEModelImport
import good.damn.editor.vector.shapes.VEShapeBase
import good.damn.editor.vector.points.VEPointIndexed
import good.damn.editor.vector.shapes.VEShapeBezierС
import good.damn.editor.vector.shapes.VEShapeLine
import good.damn.editor.vector.skeleton.VESkeleton2D
import java.io.InputStream
import java.util.LinkedList

class VEImporter {

    companion object {
        private const val mVersionImporter = 1
    }

    fun importFrom(
        inp: InputStream,
        canvasWidth: Float,
        canvasHeight: Float
    ): VEModelImport? {
        val version = inp.readU()
        if (version != mVersionImporter) {
            return null
        }

        val pointsCount = inp.readU()
        val skeletonPoints = ArrayList<VEPointIndexed>(
            pointsCount
        )

        for (i in 0..<pointsCount) {
            skeletonPoints.add(
                VEPointIndexed(
                    inp.readFraction() * canvasWidth,
                    inp.readFraction() * canvasHeight
                ).apply {
                    index = i
                }
            )
        }

        val shapesCount = inp.readU()
        val shapes = VEListShapes()

        for (i in 0..<shapesCount) {
            shapes.add(
                when (
                    inp.readU()
                ) {
                    VEShapeLine.ENCODE_TYPE -> VEShapeBezierС(
                        canvasWidth,
                        canvasHeight
                    )
                    else -> VEShapeLine(
                        canvasWidth,
                        canvasHeight
                    )
                }.apply {
                    points.fillPointsWithSkeleton(
                        skeletonPoints,
                        inp,
                        points.size
                    )
                    onDecodeObject(
                        inp
                    )
                }
            )
        }

        inp.close()

        return VEModelImport(
            skeletonPoints,
            shapes
        )
    }

}