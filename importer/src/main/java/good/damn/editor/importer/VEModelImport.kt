package good.damn.editor.importer

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

        inline fun createAnimationFromResource(
            res: Resources,
            @RawRes id: Int,
            canvasSize: Size,
            throwException: Boolean = true
        ) = res.openRawResource(id).run {
            val model = createAnimationFromStream(
                this,
                canvasSize,
                throwException
            )
            close()

            return@run model
        }

        inline fun createStaticFromResource(
            res: Resources,
            @RawRes id: Int,
            canvasSize: Size,
            throwException: Boolean = true
        ) = res.openRawResource(id).run {
            val model = createStaticFromStream(
                this,
                canvasSize,
                throwException
            )
            close()

            return@run model
        }

        inline fun createAnimationFromStream(
            inp: InputStream,
            canvasSize: Size,
            throwException: Boolean
        ) = VEImport3.animation(
            canvasSize,
            inp,
            throwException
        )

        inline fun createStaticFromStream(
            inp: InputStream,
            canvasSize: Size,
            throwException: Boolean
        ) = VEImport3.static(
            canvasSize,
            inp,
            throwException
        )
    }
}