package good.damn.sav.misc.structures.tree

open class LinkedList2<T> {

    private var mFirst: Node<T>? = null
    private var mLast: Node<T>? = null

    open fun add(
        v: T
    ) {
        if (mFirst == null) {
            mFirst = Node(v)
            return
        }

        var current: Node<T>? = mFirst
        while (true) {
            current ?: break
            if (current.next == null) {
                current.next = Node(
                    v,
                    previous = current
                )
                mLast = current.next
                break
            }
            current = current.next
        }
    }

    fun removeLast() = mLast?.run {
        mLast = previous
        previous = null
        mLast?.next = null

        value
    }

    fun forEachReversed(
        action: (T) -> Boolean
    ) {
        var current: Node<T>? = mLast
        while (current != null) {
            if (action(current.value)) {
                current = current.previous
                continue
            }
            break
        }
    }

    fun forEach(
        action: (T) -> Boolean
    ) {
        var current: Node<T>? = mFirst
        while (current != null) {
            if (action(current.value)) {
                current = current.next
                continue
            }
            break
        }
    }

    inner class Node<T>(
        var value: T,
        var previous: Node<T>? = null,
        var next: Node<T>? = null
    )
}