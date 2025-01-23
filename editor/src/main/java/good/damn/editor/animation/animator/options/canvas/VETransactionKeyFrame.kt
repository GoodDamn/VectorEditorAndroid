package good.damn.editor.animation.animator.options.canvas

import good.damn.sav.core.animation.keyframe.VEMKeyFrame

class VETransactionKeyFrame(
    private val receiver: VEITransactionReceiver
): VEITransactionRequest,
VEITransactionSender<VEMKeyFrame> {

    var receiverResult: VEITransactionResult<VEMKeyFrame>? = null

    override fun requestTransaction() {
        receiver.onReceiveTransaction()
    }

    override fun sendTransactionResult(
        result: VEMKeyFrame?
    ) {
        receiverResult?.onResultTransaction(
            result
        )
    }

}