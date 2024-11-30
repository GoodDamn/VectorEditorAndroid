package good.damn.editor.importer

import android.content.Context
import android.content.res.Resources
import androidx.annotation.RawRes
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.Size
import java.io.InputStream

data class VEModelImport(
    val skeleton: VESkeleton2D,
    val shapes: VEListShapes
) {
    companion object {
        inline fun createFromResource(
            res: Resources,
            @RawRes id: Int,
            canvasSize: Size
        ) = res.openRawResource(id).run {
            val model = createFromStream(
                this,
                canvasSize
            )
            close()

            return@run model
        }

        inline fun createFromStream(
            inp: InputStream,
            canvasSize: Size
        ) = VEImport.import(
            canvasSize,
            inp
        )
    }
}