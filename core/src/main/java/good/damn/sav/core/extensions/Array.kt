package good.damn.sav.core.extensions

import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.misc.extensions.io.readU
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