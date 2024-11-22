package good.damn.editor.export

import good.damn.sav.misc.Size
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.VEShapeFill
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.extensions.io.write
import good.damn.sav.misc.extensions.primitives.toByteArray
import good.damn.sav.misc.extensions.primitives.toDigitalFraction
import java.io.File
import java.io.FileOutputStream

class VEExport {

    companion object {
        fun export(
            skeleton: VESkeleton2D,
            shapes: List<VEShapeBase>,
            canvasSize: Size,
            file: File
        ) = FileOutputStream(
            file
        ).run {
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

            close()
        }
    }
}