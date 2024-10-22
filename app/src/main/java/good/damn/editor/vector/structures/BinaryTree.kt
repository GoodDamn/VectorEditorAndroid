package good.damn.editor.vector.structures

class BinaryTree<T>(
    private var equality: ((T,T) -> Boolean),
    private var moreThan: ((T,T) -> Boolean)
) {
    private var root: Node<T>? = null

    fun add(
        element: T
    ) {
        if (root == null) {
            root = Node(
                element,
                null,
                null,
                null
            )
            return
        }

        searchAdd(
            root!!,
            element
        )

    }

    fun search(
        node: Node<T> = root!!,
        data: T
    ): Node<T>? {
        if (equality.invoke(node.data, data)) {
            return node
        }

        if (moreThan.invoke(node.data, data)) {
            node.rightNode?.let {
                return search(it,data)
            }
            return node
        }

        node.leftNode?.let {
            return search(it,data)
        }

        return node
    }

    private fun searchAdd(
        node: Node<T>,
        data: T
    ) {
        if (moreThan.invoke(node.data, data)) {
            if (node.rightNode == null) {
                node.rightNode = Node(
                    data,
                    node,
                    null,
                    null
                )
                return
            }

            searchAdd(
                node.rightNode!!,
                data
            )
            return
        }

        if (node.leftNode == null) {
            node.leftNode = Node(
                data,
                node,
                null,
                null
            )
            return
        }

        searchAdd(
            node.leftNode!!,
            data
        )
    }


    data class Node<T>(
        var data: T,
        val parent: Node<T>?,
        var leftNode: Node<T>?,
        var rightNode: Node<T>?
    )
}