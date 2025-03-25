package good.damn.editor.importer

import good.damn.editor.importer.animation.VEIListenerImportAnimation
import good.damn.editor.importer.animation.VEImportAnimationDefault
import good.damn.editor.importer.animation.VEModelImportAnimation
import good.damn.editor.importer.exceptions.VEExceptionDifferentVersion
import good.damn.editor.importer.exceptions.VEExceptionNoAnimation
import good.damn.sav.core.VEMIdentifier
import good.damn.sav.core.VEMProjection
import good.damn.sav.core.animation.interpolators.fill.VEFillGroupObserver
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.core.shapes.primitives.VEShapeBezierQuad
import good.damn.sav.core.shapes.primitives.VEShapeLine
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.readFraction
import good.damn.sav.misc.extensions.io.readU
import java.io.InputStream

class VEImport {
    companion object {
        const val VERSION_STATIC = 3
        const val VERSION_ANIMATION = 1

        fun static(
            canvasSize: Size,
            stream: InputStream,
            throwException: Boolean
        ) = stream.run {

            val version = readU()
            if (throwException && version != VERSION_STATIC) {
                close()
                throw VEExceptionDifferentVersion(
                    version,
                    VERSION_STATIC
                )
            }

            val projection = VEMProjection(
                scale = 1.0f,
                radiusPointsScaled = 50f,
                radiusPoint = 50f
            )

            val pointsSize = readU()
            val skeleton = VESkeleton2D(
                ArrayList(pointsSize),
                projection
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
            val shapes = VEListShapes(
                projection
            )

            for (j in 0 until shapesCount) {
                defineShape(
                    readU()
                ).apply {
                    for (i in points.indices) {
                        points[i] = skeleton.getPointIndexed(
                            readU()
                        )
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
            val groups = ArrayList<VEFillGroupObserver>(
                paletteSize
            )

            for (i in 0 until paletteSize) {
                val idsCount = readU()

                val groupFill = VEFillGroupObserver().apply {
                    id = VEMIdentifier(
                        i shl 8,
                        8
                    )
                }
                val fill = VEIFill.importFill(
                    this,
                    canvasSize
                )

                for (j in 0 until idsCount) {
                    val id = readU()
                    shapes[id].fill = fill
                    groupFill.observe(
                        shapes[id]
                    )
                }

                groups.add(
                    groupFill
                )
            }

            VEModelImport(
                version.toByte(),
                skeleton,
                shapes,
                groups
            )
        }

        fun <T> animationWrapper(
            shapes: VEListShapes,
            skeleton: VESkeleton2D,
            groupsFill: List<VEFillGroupObserver>,
            stream: InputStream,
            throwException: Boolean,
            importAnimation: VEIListenerImportAnimation<T>,
            isUpper3Version: Boolean
        ) = stream.run {

            if (isUpper3Version) {
                val version = readU()
                if (throwException && version != VERSION_ANIMATION) {
                    close()
                    throw VEExceptionDifferentVersion(
                        version,
                        VERSION_ANIMATION
                    )
                }
            }

            val animSize = readU()

            if (animSize == 0) {
                close()
                if (throwException) {
                    throw VEExceptionNoAnimation()
                }
                return@run VEModelImportAnimation(
                    null
                )
            }

            val animations = ArrayList<T>(
                animSize
            )

            var type: Int
            var property: Int
            var entityIndex: Int
            var keyframesCount: Int

            for (i in 0 until animSize) {
                entityIndex = readU()
                readU().apply {
                    type = this shr 5 and 0xff
                    property = this and 0b00011111

                    // Type
                    // 0 - shape
                    // 1 - point
                    // 2 - fill
                }

                keyframesCount = readU()

                when (type) {
                    0 -> animations.add(
                        importAnimation.createShapeAnimation(
                            property,
                            keyframesCount,
                            shapes[entityIndex],
                            this
                        )
                    )

                    1 -> animations.add(
                        importAnimation.createPointAnimation(
                            property,
                            keyframesCount,
                            skeleton.getPointIndexed(
                                entityIndex
                            ),
                            this
                        )
                    )

                    2 -> animations.add(
                        importAnimation.createFillAnimation(
                            property,
                            keyframesCount,
                            groupsFill[
                                entityIndex
                            ],
                            this
                        )
                    )
                }

            }

            return@run VEModelImportAnimation(
                animations
            )
        }

        inline fun animation(
            shapes: VEListShapes,
            skeleton: VESkeleton2D,
            groupsFill: List<VEFillGroupObserver>,
            canvasSize: Size,
            stream: InputStream,
            throwException: Boolean,
            version: Byte
        ) = animationWrapper(
            shapes,
            skeleton,
            groupsFill,
            stream,
            throwException,
            VEImportAnimationDefault(
                canvasSize
            ),
            version > 3
        )
    }
}

private fun defineShape(
    type: Int
) = when (type) {
    VEShapeBezierQuad.shapeType -> VEShapeBezierQuad()
    else -> VEShapeLine()
}