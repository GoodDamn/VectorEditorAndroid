package good.damn.editor.vector.rigid

import android.graphics.PointF
import android.graphics.RectF
import good.damn.editor.vector.interfaces.rigid.VEIRigidableRect

abstract class VEPointRigid(
    var point: PointF,
    val targetRect: RectF
): VEIRigidableRect