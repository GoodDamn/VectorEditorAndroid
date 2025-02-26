package good.damn.sav.misc.structures.tree

import java.util.LinkedList

open class BinaryTree<T>(
    val balancer: BinaryTreeBalancer<T>
) {

    var size = 0
        private set

    fun add(
        element: T
    ) {
        if (root == null) {
            root = Node(
                element
            )
            size++
            return
        }

        searchAdd(
            root,
            element
        )

        size++
    }

    private var root: Node<T>? = null

    fun remove(
        it: T
    ) {
        root = searchRemove(
            root,
            it
        )
    }

    fun forEach(
        node: Node<T>? = root,
        action: (T) -> Unit
    ) {
        if (node == null) {
            return
        }

        forEach(
            node.leftNode,
            action
        )

        action.invoke(
            node.data
        )

        forEach(
            node.rightNode,
            action
        )
    }

    inner class Node<T>(
        var data: T,
        var leftNode: Node<T>? = null,
        var rightNode: Node<T>? = null
    )
}

fun <T> BinaryTree<T>.toList()
: List<T> = LinkedList<T>().apply {
    this@toList.forEach { add(it) }
}

private fun <T> BinaryTree<T>.searchRemove(
    node: BinaryTree<T>.Node<T>? = null,
    it: T
): BinaryTree<T>.Node<T>? {
    if (node == null) {
        return null
    }

    if (balancer.equals(
        node.data,
        it
    )) {

        if (node.leftNode == null) {
            val t = node.rightNode
            node.rightNode = null
            return t
        }

        if (node.rightNode == null) {
            val t = node.leftNode
            node.leftNode = null
            return t
        }

        // for in-order tree
        val s = node.rightNode.run {
            var n = this
            while (n?.leftNode != null) {
                n = n.leftNode
            }
            return@run n
        }

        node.data = s!!.data
        node.rightNode = searchRemove(
            node.rightNode,
            s.data
        )

        return node
    }

    if (balancer.moreThan(
        it, node.data
    )) {
        node.rightNode = searchRemove(
            node.rightNode,
            it
        )
        return node
    }

    node.leftNode = searchRemove(
        node.leftNode,
        it
    )

    return node
}

private fun <T> BinaryTree<T>.searchAdd(
    node: BinaryTree<T>.Node<T>? = null,
    data: T
) {
    if (node == null) {
        return
    }

    if (balancer.moreThan(
        data, node.data
    )) {
        if (node.rightNode == null) {
            node.rightNode = Node(
                data
            )
            return
        }

        searchAdd(
            node.rightNode,
            data
        )
        return
    }

    if (node.leftNode == null) {
        node.leftNode = Node(
            data
        )
        return
    }

    searchAdd(
        node.leftNode,
        data
    )
}