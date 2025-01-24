package good.damn.editor.animation.animator.options.canvas

import good.damn.sav.core.animation.keyframe.VEMKeyframe

class VETransactionKeyFrame(
    private val receiver: VEITransactionReceiver
): VEITransactionRequest,
VEITransactionSender<VEMKeyframe> {

    var receiverResult: VEITransactionResult<VEMKeyframe>? = null

    override fun requestTransaction() {
        receiver.onReceiveTransaction()
    }

    override fun sendTransactionResult(
        result: VEMKeyframe?
    ) {
        receiverResult?.onResultTransaction(
            result
        )
    }

}