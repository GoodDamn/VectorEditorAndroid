package good.damn.editor.export

import android.util.Log
import good.damn.sav.core.VEMExportAnimation
import good.damn.sav.misc.Size
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.core.shapes.primitives.VEShapeFill
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toByteArray
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import kotlinx.coroutines.android.awaitFrame
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.LinkedList

class VEExport {
    companion object {
        const val VERSION = 3

        fun export(
            skeleton: VESkeleton2D,
            shapes: List<VEShapeBase>,
            animations: List<VEMExportAnimation>,
            canvasSize: Size,
            file: File
        ) = FileOutputStream(
            file
        ).run {
            write(
                VERSION
            )

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
            close()
        }


        private inline fun exportStatic(
            os: OutputStream,
            skeleton: VESkeleton2D,
            canvasSize: Size,
            shapes: List<VEShapeBase>
        ) = os.run {

            write(
                skeleton.size
            )

            skeleton.points.forEach {
                write(
                    it.x.toDigitalFraction(
                        canvasSize.width
                    )
                )

                write(
                    it.y.toDigitalFraction(
                        canvasSize.height
                    )
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

                    if (this == VEShapeFill.shapeType) {
                        write(
                            it.points.size
                        )
                    }
                }

                it.points.forEach { point ->
                    point?.index?.apply {
                        write(
                            this
                        )
                    }
                }

                it.fill?.apply {
                    setFill[it.hashCode()]?.apply {
                        ids.add(
                            it.index.toByte()
                        )
                        return@apply
                    }

                    setFill[it.hashCode()] = FillId(
                        this,
                        LinkedList<Byte>().apply {
                            add(it.index.toByte())
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

                it.value.ids.forEach { id ->
                    write(id)
                }

                it.value.fill.write(
                    this,
                    canvasSize
                )
            }
        }

        private inline fun exportAnimation(
            os: OutputStream,
            canvasSize: Size,
            animations: List<VEMExportAnimation>,
        ) = os.run {

            if (animations.isEmpty()) {
                return@run
            }

            write(
                animations.size
            )

            var type: Byte
            var props: Int
            var id: Int
            animations.forEach {
                type = if (it.entity.index >= 0xffff) {
                    id = (it.entity.index shr 16) and 0xff
                    0 // shape
                } else {
                    id = it.entity.index
                    1
                } // point

                Log.d("VEExport", "exportAnimation: $id")
                write(id)

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