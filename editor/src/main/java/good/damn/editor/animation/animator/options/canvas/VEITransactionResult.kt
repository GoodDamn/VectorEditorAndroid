package good.damn.editor.animation.animator.options.canvas

interface VEITransactionResult<T> {
    fun onResultTransaction(
        result: T?
    )
}