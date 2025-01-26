package good.damn.editor.export

import good.damn.sav.core.VEMExportAnimation
import good.damn.sav.misc.Size
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.VEShapeFill
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toByteArray
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class VEExport {
    companion object {
        const val VERSION = 2

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

                it.points.forEach {
                    it?.index?.apply {
                        write(
                            this
                        )
                    }
                }

                write(
                    it.color.toByteArray()
                )

                write(
                    it.strokeWidth.toDigitalFraction(
                        canvasSize.width
                    )
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

            animations.forEach {
                write(
                    it.entity.index
                )

                write(
                    it.propertyId
                )

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