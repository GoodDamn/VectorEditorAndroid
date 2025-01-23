package good.damn.editor.animation.animator.options.canvas

interface VEITransactionSender<T> {
    fun sendTransactionResult(
        result: T?
    )
}