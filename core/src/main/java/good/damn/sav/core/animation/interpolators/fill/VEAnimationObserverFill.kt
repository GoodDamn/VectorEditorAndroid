package good.damn.sav.core.animation.interpolators.fill

import good.damn.sav.core.VEIExportableAnimationEntity
import good.damn.sav.core.VEIIdentifiable
import good.damn.sav.core.VEMIdentifier
import good.damn.sav.core.lists.VEListShapes
import good.damn.sav.core.shapes.VEShapeBase
import good.damn.sav.core.shapes.fill.VEIFill
import good.damn.sav.misc.Size
import good.damn.sav.misc.extensions.io.readU
import java.io.InputStream
import java.io.OutputStream
import java.util.LinkedList

class VEAnimationObserverFill
: VEIIdentifiable,
VEIExportableAnimationEntity {

    companion object {
        fun import(
            inp: InputStream,
            shapes: VEListShapes,
            entityId: Int
        ) = VEAnimationObserverFill().apply {
            id = VEMIdentifier(
                entityId,
                8
            )
            inp.readU().apply {
                for (i in 0 until this) {
                    observe(shapes[inp.readU()])
                }
            }

        }
    }

    private val mList = LinkedList<VEShapeBase>()

    override var id = VEMIdentifier(
        1 shl 8,
        8
    )

    override val typeEntity: Byte
        get() = 2

    override fun writeId(
        os: OutputStream
    ) = id.write(os)

    override fun write(
        os: OutputStream
    ) = os.run {

        write(
            mList.size
        )

        mList.forEach {
            it.id.write(
                this
            )
        }
    }

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