package good.damn.editor.vector.porters

import good.damn.editor.vector.extensions.io.readU
import good.damn.editor.vector.paints.VEPaintBase
import good.damn.editor.vector.paints.VEPaintCircle
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
        var bytesCount = 0

        var vectorDataSize: Int
        var vectorType: Int

        for (i in 0..<vectorsCount) {
            vectorDataSize = inp.readU()
            vectorType = inp.readU()
            list.add(
                when (vectorType) {
                    VEPaintLine.ENCODE_TYPE.toInt() -> VEPaintLine(
                        canvasWidth,
                        canvasHeight
                    )
                    else -> VEPaintCircle(
                        canvasWidth,
                        canvasHeight
                    )
                }.apply {
                    onDecodeObject(inp)
                }
            )

            bytesCount += vectorDataSize
        }

        inp.close()
        return list
    }

}