package good.damn.editor.transaction

interface VEITransactionResult<T> {
    fun onResultTransaction(
        result: T?
    )
}