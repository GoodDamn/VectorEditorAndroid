package good.damn.editor.importer

import good.damn.editor.importer.animation.VEIListenerImportAnimation
import good.damn.editor.importer.animation.VEImportAnimationDefault
import good.damn.editor.importer.animation.VEModelImportAnimation
import good.damn.editor.importer.exceptions.VEExceptionDifferentVersion
import good.damn.editor.importer.exceptions.VEExceptionNoAnimation
import good.damn.sav.core.VEMIdentifier
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.core.shapes.primitives.VEShapeBezierQuad
import good.damn.sav.core.shapes.primitives.VEShapeFill
import good.damn.sav.core.shapes.primitives.VEShapeLine
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.readFraction
import good.damn.sav.misc.extensions.io.readU
import java.io.InputStream

class VEImport2 {
    companion object {
        const val VERSION = 3

        fun static(
            canvasSize: Size,
            stream: InputStream,
            throwException: Boolean
        ) = stream.run {

            val version = readU()
            if (throwException && version != VERSION) {
                close()
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
                    ).apply {
                        id = VEMIdentifier(
                            j,
                            0
                        )
                    }
                )
            }

            val shapesCount = readU()
            val shapes = VEListShapes()

            for (j in 0 until shapesCount) {
                defineShape(
                    readU()
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

                    id = VEMIdentifier(
                        j shl 16,
                        16
                    )

                    strokeWidth = readFraction() * canvasSize.width

                    shapes.add(
                        this
                    )
                }
            }

            // Filling
            val paletteSize = readU()
            for (i in 0 until paletteSize) {
                val idsCount = readU()

                val fill = VEIFill.importFill(
                    this,
                    canvasSize
                )

                for (j in 0 until idsCount) {
                    val id = readU()
                    shapes[id].fill = fill
                }
            }

            VEModelImport(
                skeleton,
                shapes
            )
        }

        fun <T> animationWrapper(
            canvasSize: Size,
            stream: InputStream,
            throwException: Boolean,
            importAnimation: VEIListenerImportAnimation<T>
        ) = stream.run {
            val model = static(
                canvasSize,
                stream,
                throwException
            )
            val animSize = readU()

            if (animSize == 0) {
                close()
                if (throwException) {
                    throw VEExceptionNoAnimation()
                }
                return@run VEModelImportAnimation(
                    model,
                    null
                )
            }

            val animations = ArrayList<T>(
                animSize
            )

            var type: Int
            var property: Int
            var entityId: Int
            var keyframesCount: Int

            for (i in 0 until animSize) {
                entityId = readU()
                readU().apply {
                    type = this shr 5 and 0xff
                    property = this and 0b00011111

                    // 0 - shape
                    // 1 - point
                    entityId = entityId shl if (
                        type == 0
                    ) 16 else 0
                }

                keyframesCount = readU()

                if (type == 1) {
                    animations.add(
                        importAnimation.createPointAnimation(
                            property,
                            keyframesCount,
                            model.skeleton.getPointIndexed(
                                entityId
                            ),
                            this
                        )
                    )
                    continue
                }

                animations.add(
                    importAnimation.createShapeAnimation(
                        property,
                        keyframesCount,
                        model.shapes[(entityId shr 16) and 0xff],
                        this
                    )
                )
            }

            return@run VEModelImportAnimation(
                model,
                animations
            )
        }

        inline fun animation(
            canvasSize: Size,
            stream: InputStream,
            throwException: Boolean
        ) = animationWrapper(
            canvasSize,
            stream,
            throwException,
            VEImportAnimationDefault(
                canvasSize
            )
        )
    }
}

private fun defineShape(
    type: Int
) = when (type) {
    VEShapeFill.shapeType -> VEShapeFill()
    VEShapeBezierQuad.shapeType -> VEShapeBezierQuad()
    else -> VEShapeLine()
}