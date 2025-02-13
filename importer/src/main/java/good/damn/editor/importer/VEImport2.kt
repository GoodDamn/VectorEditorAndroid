package good.damn.editor.importer

import android.graphics.PointF
import good.damn.editor.importer.exceptions.VEExceptionDifferentVersion
import good.damn.editor.importer.exceptions.VEExceptionNoAnimation
import good.damn.sav.core.VEMIdentifier
import good.damn.sav.core.animation.animators.VEAnimatorInterpolation
import good.damn.sav.core.animation.animators.VEIListenerAnimation
import good.damn.sav.core.animation.interpolators.fill.VEAnimationInterpolatorFill
import good.damn.sav.core.animation.interpolators.VEAnimationInterpolatorPosition
import good.damn.sav.core.animation.interpolators.VEAnimationInterpolatorStrokeWidth
import good.damn.sav.core.animation.interpolators.VEIAnimationInterpolator
import good.damn.sav.core.animation.interpolators.fill.VEMFillColorPriority
import good.damn.sav.core.animation.interpolators.fill.VEMFillGradientPriority
import good.damn.sav.core.animation.keyframe.VEIKeyframe
import good.damn.sav.core.animation.keyframe.fill.VEMKeyframeFill
import good.damn.sav.core.animation.keyframe.VEMKeyframePosition
import good.damn.sav.core.animation.keyframe.VEMKeyframeWidth
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.fill.VEMFillColor
import good.damn.sav.core.shapes.fill.VEMFillGradientLinear
import good.damn.sav.core.shapes.primitives.VEShapeBezierQuad
import good.damn.sav.core.shapes.primitives.VEShapeFill
import good.damn.sav.core.shapes.primitives.VEShapeLine
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.readFraction
import good.damn.sav.misc.extensions.io.readInt32
import good.damn.sav.misc.extensions.io.readU
import java.io.InputStream

class VEImport2 {
    companion object {
        const val VERSION = 3

        fun animation(
            canvasSize: Size,
            stream: InputStream,
            throwException: Boolean
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

            val animations = ArrayList<VEIListenerAnimation>(
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
                        VEAnimatorInterpolation(
                            createPointAnimation(
                                property,
                                this,
                                keyframesCount,
                                entityId,
                                canvasSize,
                                model.skeleton
                            )
                        )
                    )
                    continue
                }

                animations.add(
                    VEAnimatorInterpolation(
                        createShapeAnimation(
                            property,
                            this,
                            keyframesCount,
                            model.shapes[(entityId shr 16) and 0xff],
                            canvasSize
                        )
                    )
                )
            }

            return@run VEModelImportAnimation(
                model,
                animations
            )
        }

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

                val fill = importFill(
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
    }
}

private fun createShapeAnimation(
    property: Int,
    inp: InputStream,
    keyframesCount: Int,
    shape: VEShapeBase,
    size: Size
) = when (property) {
    0 -> extractAnimation(
        inp,
        keyframesCount,
        keyframeCreate = { stream, fraction ->
            importFillKeyframe(
                fraction,
                stream,
                size
            )
        }
    ) { start, end ->
        VEAnimationInterpolatorFill(
            start,
            end,
            shape
        )
    }

    else -> extractAnimation(
        inp,
        keyframesCount,
        keyframeCreate = { stream, fraction ->
            VEMKeyframeWidth.import(
                size,
                fraction,
                stream
            )
        }
    ) { start, end ->
        VEAnimationInterpolatorStrokeWidth(
            start,
            end,
            shape
        )
    }
}

private fun createPointAnimation(
    property: Int,
    inp: InputStream,
    keyframesCount: Int,
    id: Int,
    size: Size,
    skeleton: VESkeleton2D
) = extractAnimation(
    inp,
    keyframesCount,
    keyframeCreate = { stream, factor ->
        VEMKeyframePosition.import(
            size,
            factor,
            stream
        )
    }
) { start, end ->
    VEAnimationInterpolatorPosition(
        start,
        end,
        skeleton.getPointIndexed(
            id
        )
    )
}

private fun <
    INTERPOLATOR: VEIAnimationInterpolator,
    KEYFRAME: VEIKeyframe
> extractAnimation(
    inp: InputStream,
    keyframesCount: Int,
    keyframeCreate: (
        stream: InputStream,
        factor: Float
    ) -> KEYFRAME,
    interpolatorCreate: (
        KEYFRAME,
        KEYFRAME
    ) -> INTERPOLATOR
) = ArrayList<INTERPOLATOR>(
    keyframesCount - 1
).apply {
    var start: KEYFRAME? = null
    var end: KEYFRAME
    for (j in 0 until keyframesCount) {
        if (start == null) {
            start = keyframeCreate(
                inp,
                inp.readFraction()
            )
            continue
        }

        end = keyframeCreate(
            inp,
            inp.readFraction()
        )

        add(
            interpolatorCreate(
                start,
                end
            )
        )

        start = end
    }
}

private fun defineShape(
    type: Int
) = when (type) {
    VEShapeFill.shapeType -> VEShapeFill()
    VEShapeBezierQuad.shapeType -> VEShapeBezierQuad()
    else -> VEShapeLine()
}

private fun importFillKeyframe(
    factor: Float,
    inp: InputStream,
    canvasSize: Size
) = importFill(
    inp,
    canvasSize
).run {
    VEMKeyframeFill(
        factor,
        this,
        createPriority()
    )
}

private fun importFill(
    inp: InputStream,
    canvasSize: Size
) = inp.run {
    when (readU()) {
        VEMFillGradientLinear.TYPE -> {
            val p0x = readFraction() * canvasSize.width
            val p0y = readFraction() * canvasSize.height

            val p1x = readFraction() * canvasSize.width
            val p1y = readFraction() * canvasSize.height

            val s = readU()

            VEMFillGradientLinear(
                p0x,
                p0y,
                p1x,
                p1y,
                IntArray(s).apply {
                    for (ic in indices) {
                        this[ic] = readInt32(
                            ByteArray(4)
                        )
                    }
                },
                FloatArray(s).apply {
                    for (ic in indices) {
                        this[ic] = readFraction()
                    }
                }
            )
        }

        else -> {
            val b = ByteArray(4)
            readInt32(b)
            VEMFillColor(b)
        }
    }
}