package good.damn.editor.vector.interfaces

import good.damn.editor.vector.shapes.VEShapeBase

interface VEIInstansable {
    fun onReadInstance(
        instance: VEShapeBase
    )
}