package good.damn.sav.misc.scopes

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class TimeScope(
    override val coroutineContext: CoroutineContext
): CoroutineScope {

    val deltaTime: Long
        get() = System.currentTimeMillis() - mPrevTime

    private var mPrevTime = 0L

    fun remember() {
        mPrevTime = System.currentTimeMillis()
    }
}