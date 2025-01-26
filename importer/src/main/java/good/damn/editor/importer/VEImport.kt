package good.damn.editor.importer

import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBezierQuad
import good.damn.sav.core.shapes.VEShapeFill
import good.damn.sav.core.shapes.VEShapeLine
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.readFraction
import good.damn.sav.misc.extensions.io.readInt32
import good.damn.sav.misc.extensions.io.readU
import java.io.InputStream

class VEImport {
    companion object {
        const val VERSION = 2
        fun import(
            canvasSize: Size,
            stream: InputStream
        ) = stream.run {

            val version = readU()
            if (version != VERSION) {
                throw VEExceptionDifferentVersion(
                    version,
                    VERSION
                )
            }

            val pointsSize = readU()
            val skeleton = VESkeleton2D(
                ArrayList(pointsSize)
            )

            for (j in 0 until pointsSize) {
                skeleton.addSkeletonPoint(
                    VEPointIndexed(
                        readFraction() * canvasSize.width,
                        readFraction() * canvasSize.height
                    )
                )
            }

            val shapesCount = readU()
            val shapes = VEListShapes()

            val buffer4 = ByteArray(4)

            for (j in 0 until shapesCount) {
                defineShape(
                    readU(),
                    canvasSize
                ).apply {

                    if (shapeType() == VEShapeFill.shapeType) {
                        val p = readU()
                        for (i in 0 until p) {
                            points.add(
                                skeleton.getPointIndexed(
                                    readU()
                                )
                            )
                        }
                    } else {
                        for (i in points.indices) {
                            points[i] = skeleton.getPointIndexed(
                                readU()
                            )
                        }
                    }

                    color = readInt32(
                        buffer4
                    )

                    strokeWidth = readFraction() * canvasSize.width

                    shapes.add(
                        this
                    )
                }
            }

            VEModelImport(
                skeleton,
                shapes
            )
        }


        private inline fun defineShape(
            type: Int,
            canvasSize: Size
        ) = when (type) {
            VEShapeFill.shapeType -> VEShapeFill()
            VEShapeBezierQuad.shapeType -> VEShapeBezierQuad()
            else -> VEShapeLine()
        }
    }
}