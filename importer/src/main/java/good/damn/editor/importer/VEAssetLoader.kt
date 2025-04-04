package good.damn.editor.importer

import android.content.ContentResolver
import android.net.Uri
import good.damn.editor.importer.animation.VEIListenerImportAnimation
import good.damn.sav.core.animation.interpolators.fill.VEFillGroupObserver
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.skeleton.VESkeleton2D
import good.damn.sav.misc.Size
import java.io.InputStream

class VEAssetLoader {
    companion object {

        fun <T> loadAssetStaticAnimation(
            canvasSize: Size,
            contentResolver: ContentResolver,
            importAnimation: VEIListenerImportAnimation<T>,
            uri: Uri
        ) = loadAsset(
            contentResolver,
            uri
        ) {
            val static = VEImport.static(
                canvasSize,
                it,
                false
            )

            return@loadAsset Pair(
                static,
                VEImport.animationWrapper(
                    static.shapes,
                    static.skeleton,
                    static.groupsFill,
                    it,
                    false,
                    importAnimation,
                    static.version > 3
                )
            )
        }

        fun loadAssetStatic(
            canvasSize: Size,
            contentResolver: ContentResolver,
            uri: Uri
        ) = loadAsset(
            contentResolver,
            uri
        ) {
            return@loadAsset VEImport.static(
                canvasSize,
                it,
                false
            )
        }

        fun <T> loadAssetAnimation(
            shapes: VEListShapes,
            skeleton: VESkeleton2D,
            groupsFill: List<VEFillGroupObserver>,
            importer: VEIListenerImportAnimation<T>,
            contentResolver: ContentResolver,
            uri: Uri
        ) = loadAsset(
            contentResolver,
            uri
        ) { stream ->
            return@loadAsset VEImport.animationWrapper(
                shapes,
                skeleton,
                groupsFill,
                stream,
                false,
                importer,
                true
            )
        }

        private inline fun <reified T> loadAsset(
            contentResolver: ContentResolver,
            uri: Uri,
            process: (InputStream) -> T
        ) = contentResolver.openInputStream(
            uri
        )?.run {
            val result = process(
                this
            )

            close()

            return@run result
        }
    }
}