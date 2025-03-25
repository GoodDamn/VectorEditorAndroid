package good.damn.editor.importer

import android.content.res.Resources
import androidx.annotation.RawRes
import good.damn.sav.core.animation.interpolators.fill.VEFillGroupObserver
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.Size
import java.io.InputStream

data class VEModelImport(
    val version: Byte,
    val skeleton: VESkeleton2D,
    val shapes: VEListShapes,
    val groupsFill: List<VEFillGroupObserver>
) {
    companion object {

        inline fun createAnimationFromResource(
            shapes: VEListShapes,
            skeleton: VESkeleton2D,
            groupsFill: List<VEFillGroupObserver>,
            res: Resources,
            @RawRes id: Int,
            canvasSize: Size,
            throwException: Boolean = true,
            version: Byte
        ) = res.openRawResource(id).run {
            val model = createAnimationFromStream(
                shapes,
                skeleton,
                groupsFill,
                this,
                canvasSize,
                throwException,
                version
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
            shapes: VEListShapes,
            skeleton: VESkeleton2D,
            groupsFill: List<VEFillGroupObserver>,
            inp: InputStream,
            canvasSize: Size,
            throwException: Boolean,
            version: Byte
        ) = VEImport.animation(
            shapes,
            skeleton,
            groupsFill,
            canvasSize,
            inp,
            throwException,
            version
        )

        inline fun createStaticFromStream(
            inp: InputStream,
            canvasSize: Size,
            throwException: Boolean
        ) = VEImport.static(
            canvasSize,
            inp,
            throwException
        )
    }
}