package good.damn.editor.vector.extensions

import good.damn.editor.vector.extensions.io.readU
import good.damn.editor.vector.points.VEPointIndexed
import good.damn.editor.vector.skeleton.VESkeleton2D
import java.io.InputStream

inline fun Array<VEPointIndexed?>.fillPointsWithSkeleton(
    skeletonPoints: MutableList<VEPointIndexed>,
    inp: InputStream,
    numPoints: Int
) {
   for (i in 0..<numPoints) {
       set(
           i,
           VEPointIndexed(
               skeletonPoints[
                   inp.readU()
               ]
           )
       )
   }
}