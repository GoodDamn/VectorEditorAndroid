package good.damn.sav.core.animation.interpolators.fill

import good.damn.sav.core.VEIExportableAnimationEntity
import good.damn.sav.core.VEIIdentifiable
import good.damn.sav.core.VEMIdentifier
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.misc.extensions.io.readU
import java.io.InputStream
import java.io.OutputStream
import java.util.LinkedList

class VEFillGroupObserver
: VEIIdentifiable,
VEIExportableAnimationEntity {

    private val mList = LinkedList<VEShapeBase>()

    override var id = VEMIdentifier(
        0 shl 8,
        8
    )

    override val typeEntity: Byte
        get() = 2

    override fun writeId(
        os: OutputStream
    ) = id.write(os)


    var value: VEIFill? = null
        set(v) {
            field = v
            mList.forEach {
                it.fill = v
            }
        }

    fun observe(
        shape: VEShapeBase
    ) {
        mList.add(
            shape
        )
    }

    fun removeObservers() {
        mList.clear()
    }

    fun removeObserver(
        shape: VEShapeBase
    ) {
        mList.remove(
            shape
        )
    }

}