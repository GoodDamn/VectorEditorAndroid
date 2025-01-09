package good.damn.editor.vector.launchers

import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class VELauncherPermission(
    private val onResultPermission: VEListenerOnResultPermission
): ActivityResultCallback<
    Map<String, Boolean>
> {

    private lateinit var mLauncher: ActivityResultLauncher<
        Array<String>
    >

    fun register(
        activity: AppCompatActivity
    ) {
        mLauncher = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
            this
        )
    }

    fun launch(
        permissions: Array<String>
    ) = mLauncher.launch(
        permissions
    )

    fun launch(
        permission: String
    ) {
        mLauncher.launch(
            arrayOf(permission)
        )
    }

    override fun onActivityResult(
        result: Map<String, Boolean>
    ) {
        result.forEach {
            onResultPermission.onResultPermission(
                it.key,
                it.value
            )
        }
    }

}