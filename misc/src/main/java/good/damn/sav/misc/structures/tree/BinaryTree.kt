package good.damn.sav.misc.structures.tree

import java.util.Stack
import java.util.function.Consumer

class BinaryTree<T>(
    val equality: ((T,T) -> Boolean),
    val moreThan: ((T,T) -> Boolean)
) {
    private var root: Node<T>? = null

    fun add(
        element: T
    ) {
        if (root == null) {
            root = Node(
                element
            )
            return
        }

        searchAdd(
            root,
            element
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
}

private fun <T> BinaryTree<T>.searchAdd(
    node: Node<T>? = null,
    data: T
) {
    if (node == null) {
        return
    }

    if (moreThan.invoke(data, node.data)) {
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

data class Node<T>(
    var data: T,
    var leftNode: Node<T>? = null,
    var rightNode: Node<T>? = null
)