package good.damn.editor.vector.extensions.primitives

import good.damn.editor.vector.java.utils.VEUtilsIntJava

fun Int.toByteArray() = VEUtilsIntJava.int32(
    this
)