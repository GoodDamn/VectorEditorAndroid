package good.damn.editor.export

import good.damn.sav.core.VEMExportAnimation
import good.damn.sav.misc.Size
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import good.damn.sav.misc.extensions.writeToStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.LinkedList

class VEExport {
    companion object {
        const val VERSION_STATIC = 3
        const val VERSION_ANIMATION = 1

        fun exportStaticAnimation(
            skeleton: VESkeleton2D,
            shapes: List<VEShapeBase>,
            animations: List<VEMExportAnimation>,
            canvasSize: Size,
            os: OutputStream
        ) = os.run {
            exportStatic(
                this,
                skeleton,
                canvasSize,
                shapes
            )

            exportAnimation(
                this,
                canvasSize,
                animations
            )
        }

        fun exportStatic(
            os: OutputStream,
            skeleton: VESkeleton2D,
            canvasSize: Size,
            shapes: List<VEShapeBase>
        ) = os.run {

            write(
                VERSION_STATIC
            )

            write(
                skeleton.size
            )

            skeleton.points.forEach {
                it.writeToStream(
                    this,
                    canvasSize
                )
            }

            write(
                shapes.size
            )

            val setFill = HashMap<Int, FillId>()

            shapes.forEach {
                it.shapeType().apply {
                    write(
                        this
                    )
                }

                it.points.forEach { point ->
                    point?.id?.write(
                        this
                    )
                }

                it.fill?.apply {
                    setFill[
                        hashCode()
                    ]?.let { fill ->
                        fill.ids.add(
                            it.id.normalized.toByte()
                        )
                        return@apply
                    }

                    setFill[hashCode()] = FillId(
                        this,
                        LinkedList<Byte>().apply {
                            add(it.id.normalized.toByte())
                        }
                    )
                }

                write(
                    it.strokeWidth.toDigitalFraction(
                        canvasSize.width
                    )
                )
            }

            write(
                setFill.size
            )

            setFill.forEach {
                write(
                    it.value.ids.size
                )

                it.value.fill.write(
                    this,
                    canvasSize
                )

                it.value.ids.forEach { id ->
                    write(id)
                }
            }
        }

        fun exportAnimation(
            os: OutputStream,
            canvasSize: Size,
            animations: List<VEMExportAnimation>,
        ) = os.run {

            write(
                VERSION_ANIMATION
            )

            write(
                animations.size
            )

            var type: Byte
            var props: Int
            animations.forEach {

                type = it.entity.typeEntity

                // 16 -> 0 - shape
                // 8 -> 2 - fill
                // else -> 1 - point

                it.entity.writeId(
                    this
                )

                props = 0
                props = type.toInt() shl 5 or (
                    it.propertyId.toInt() and 0b00011111
                )
                write(props)
                write(it.keyframes.size)
                it.keyframes.forEach { keyframe ->
                    keyframe.export(
                        this,
                        canvasSize
                    )
                }
            }
        }
    }
}

private data class FillId(
    val fill: VEIFill,
    val ids: LinkedList<Byte>
) {
    override fun hashCode() = fill.hashCode()
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as FillId
        return fill == other.fill
    }
}