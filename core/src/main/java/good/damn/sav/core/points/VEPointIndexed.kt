package good.damn.sav.core.points

import android.graphics.PointF
import good.damn.sav.core.VEIExportableAnimationEntity
import good.damn.sav.core.VEIExportableKeyframe
import good.damn.sav.core.VEIIdentifiable
import good.damn.sav.core.VEMIdentifier
import good.damn.sav.misc.Size
import java.io.OutputStream

class VEPointIndexed(
    x: Float,
    y: Float
): PointF(
    x,y
), VEIIdentifiable,
VEIExportableAnimationEntity {

    override var id = VEMIdentifier.ZERO
    override val typeEntity: Byte
        get() = 1

    constructor(
        point: PointF
    ): this(
        point.x,
        point.y
    )

    override fun write(
        os: OutputStream
    ) = Unit

    override fun writeId(
        os: OutputStream
    ) = id.write(os)
}