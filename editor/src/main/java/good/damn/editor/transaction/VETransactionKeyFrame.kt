package good.damn.editor.transaction

import good.damn.sav.core.animation.keyframe.VEIKeyframe

class VETransactionKeyFrame(
    private val receiver: VEITransactionReceiver
): VEITransactionRequest,
    VEITransactionSender<VEIKeyframe> {

    var receiverResult: VEITransactionResult<VEIKeyframe>? = null

    override fun requestTransaction() {
        receiver.onReceiveTransaction()
    }

    override fun sendTransactionResult(
        result: VEIKeyframe?
    ) {
        receiverResult?.onResultTransaction(
            result
        )
    }

}