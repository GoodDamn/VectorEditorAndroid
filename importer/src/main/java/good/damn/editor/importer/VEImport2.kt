package good.damn.editor.importer

import android.graphics.PointF
import android.util.Log
import good.damn.editor.importer.exceptions.VEExceptionDifferentVersion
import good.damn.editor.importer.exceptions.VEExceptionNoAnimation
import good.damn.sav.core.VEMIdentifier
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
            val buffer4 = ByteArray(4)
            val paletteSize = readU()
            for (i in 0 until paletteSize) {
                val idsCount = readU()

                val fill = when (readU()) {
                    VEMFillGradientLinear.TYPE -> {

                        val p = PointF(
                            readFraction() * canvasSize.width,
                            readFraction() * canvasSize.height
                        )

                        val pp = PointF(
                            readFraction() * canvasSize.width,
                            readFraction() * canvasSize.height
                        )

                        val s = readU()

                        val colors = IntArray(s).apply {
                            for (ic in indices) {
                                this[ic] = readInt32(buffer4)
                            }
                        }

                        val positions = FloatArray(s).apply {
                            for (ic in indices) {
                                this[ic] = readFraction()
                            }
                        }

                        VEMFillGradientLinear(
                            p,
                            pp,
                            colors,
                            positions
                        )
                    }
                    else -> VEMFillColor(
                        readInt32(buffer4)
                    )
                }

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
    )
    }

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