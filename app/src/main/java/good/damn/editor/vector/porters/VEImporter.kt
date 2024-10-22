package good.damn.editor.vector.porters

import android.util.Log
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
import kotlin.math.log

class VEImporter {

    companion object {
        private const val mVersionImporter = 1
        private const val TAG = "VEImporter"
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

        Log.d(TAG, "importFrom: POINTS_COUNT: $pointsCount")
        
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

        Log.d(TAG, "importFrom: $skeletonPoints")
        
        val shapesCount = inp.readU()
        val shapes = VEListShapes()

        Log.d(TAG, "importFrom: SHAPES_COUNT: $shapesCount")
        
        for (i in 0..<shapesCount) {
            val vectorType = inp.readU()
            shapes.add(
                when (
                    vectorType
                ) {
                    VEShapeBezierС.ENCODE_TYPE -> VEShapeBezierС(
                        canvasWidth,
                        canvasHeight
                    )
                    else -> VEShapeLine(
                        canvasWidth,
                        canvasHeight
                    )
                }.apply {
                    Log.d(TAG, "importFrom: fill_points: ${points.size}; vectorType: $vectorType; $this")
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