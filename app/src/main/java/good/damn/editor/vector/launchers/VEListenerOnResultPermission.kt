package good.damn.editor.vector.launchers

interface VEListenerOnResultPermission {
    fun onResultPermission(
        permission: String,
        isGranted: Boolean
    )
}