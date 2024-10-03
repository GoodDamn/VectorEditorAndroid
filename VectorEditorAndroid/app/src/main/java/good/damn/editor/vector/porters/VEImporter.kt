package good.damn.editor.vector.porters

import good.damn.editor.vector.extensions.io.readU
import good.damn.editor.vector.paints.VEPaintBase
import good.damn.editor.vector.paints.VEPaintBezierС
import good.damn.editor.vector.paints.VEPaintLine
import java.io.InputStream
import java.util.LinkedList

class VEImporter {

    companion object {
        private const val mVersionImporter = 1
    }

    fun importFrom(
        inp: InputStream,
        canvasWidth: Float,
        canvasHeight: Float
    ): LinkedList<VEPaintBase>? {
        val version = inp.readU()
        if (version != mVersionImporter) {
            return null
        }

        val vectorsCount = inp.readU()
        val list = LinkedList<VEPaintBase>()

        var vectorType: Int

        for (i in 0..<vectorsCount) {
            vectorType = inp.readU()
            list.add(
                when (vectorType) {
                    VEPaintLine.ENCODE_TYPE -> VEPaintLine(
                        canvasWidth,
                        canvasHeight
                    )
                    else -> VEPaintBezierС(
                        canvasWidth,
                        canvasHeight
                    )
                }.apply {
                    onDecodeObject(inp)
                }
            )
        }

        inp.close()
        return list
    }

}