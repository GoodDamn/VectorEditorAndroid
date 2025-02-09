package good.damn.editor.importer

import good.damn.editor.importer.exceptions.VEExceptionDifferentVersion
import good.damn.editor.importer.exceptions.VEExceptionNoAnimation
import good.damn.sav.core.animation.animators.VEAnimatorColor
import good.damn.sav.core.animation.animators.VEAnimatorPosition
import good.damn.sav.core.animation.animators.VEAnimatorWidth
import good.damn.sav.core.animation.animators.VEIListenerAnimation
import good.damn.sav.core.animation.keyframe.VEIKeyframe
import good.damn.sav.core.animation.keyframe.VEMKeyframeColor
import good.damn.sav.core.animation.keyframe.VEMKeyframePosition
import good.damn.sav.core.animation.keyframe.VEMKeyframeWidth
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.core.shapes.VEShapeBase
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

            if (animSize == -1) {
                close()
                if (throwException) {
                    throw VEExceptionNoAnimation()
                }
                return@run null
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
                    createPointAnimation(
                        property,
                        this,
                        keyframesCount,
                        animations,
                        entityId,
                        canvasSize,
                        model
                    )
                    continue
                }

                createShapeAnimation(
                    property,
                    this,
                    keyframesCount,
                    animations,
                    model.shapes[(entityId shr 16) and 0xff],
                    canvasSize
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
                        index = j
                    }
                )
            }

            val shapesCount = readU()
            val shapes = VEListShapes()

            val buffer4 = ByteArray(4)

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

                    index = 0
                    index = (j shl 16) or 0x0000ffff

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
    }
}

private inline fun createShapeAnimation(
    property: Int,
    inp: InputStream,
    keyframesCount: Int,
    animations: ArrayList<VEIListenerAnimation>,
    shape: VEShapeBase,
    size: Size
) = when (property) {
    0 -> extractAnimation(
        inp,
        keyframesCount,
        animations,
        keyframeCreate = { stream, fraction ->
            VEMKeyframeColor.import(
                fraction,
                stream
            )
        }
    ) { VEAnimatorColor(
        shape,
        it
    )}

    else -> extractAnimation(
        inp,
        keyframesCount,
        animations,
        keyframeCreate = { stream, fraction ->
            VEMKeyframeWidth.import(
                size,
                fraction,
                stream
            )
        }
    ) {
        VEAnimatorWidth(
            shape,
            it
        )
    }
}

private inline fun createPointAnimation(
    property: Int,
    inp: InputStream,
    keyframesCount: Int,
    animations: ArrayList<VEIListenerAnimation>,
    id: Int,
    size: Size,
    model: VEModelImport
) = extractAnimation(
    inp,
    keyframesCount,
    animations,
    keyframeCreate = { stream, factor ->
        VEMKeyframePosition.import(
            size,
            factor,
            stream
        )
    }
) {
    VEAnimatorPosition(
        model.skeleton.getPointIndexed(id),
        it
    )
}

private inline fun <
    reified KEYFRAME: VEIKeyframe,
    reified ANIMATOR: VEIListenerAnimation
> extractAnimation(
    inp: InputStream,
    keyframesCount: Int,
    animations: ArrayList<VEIListenerAnimation>,
    keyframeCreate: (
        stream: InputStream,
        factor: Float
    ) -> KEYFRAME,
    animationCreate: (List<KEYFRAME>) -> ANIMATOR
) {
    val keyframes = ArrayList<KEYFRAME>(
        keyframesCount
    )

    for (j in 0 until keyframesCount) {
        keyframes.add(
            keyframeCreate(
                inp,
                inp.readFraction()
            )
        )
    }

    animations.add(
        animationCreate(
            keyframes
        )
    )
}

private inline fun defineShape(
    type: Int
) = when (type) {
    VEShapeFill.shapeType -> VEShapeFill()
    VEShapeBezierQuad.shapeType -> VEShapeBezierQuad()
    else -> VEShapeLine()
}