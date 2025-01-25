package good.damn.editor.transaction

interface VEITransactionSender<T> {
    fun sendTransactionResult(
        result: T?
    )
}