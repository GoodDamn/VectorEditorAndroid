package good.damn.sav.core.utils

import android.graphics.PointF
import good.damn.sav.core.points.VEPointIndexed
import good.damn.sav.misc.extensions.angle
import good.damn.sav.misc.extensions.minMax
import kotlin.math.cos
import kotlin.math.sin

class VEUtilsHit {
    companion object {
        inline fun poly(
            x: Float,
            y: Float,
            points: List<PointF?>
        ): Boolean {
            if (points.size < 3) {
                return false
            }

            var i = 0
            var j = points.size-1
            var isInside = false

            var pi: PointF
            var pj: PointF

            while (i < points.size) {
                pi = points[i]
                    ?: continue

                pj = points[j]
                    ?: continue

                if (
                    ((pi.y > y) != (pj.y > y)) &&
                    (x < (pj.x - pi.x) * (y - pi.y) / (pj.y - pi.y) + pi.x)
                ) {
                    isInside = !isInside
                }

                j = i++
            }

            return isInside
        }

        inline fun line(
            x: Float,
            y: Float,
            p: PointF,
            pp: PointF,
            lineWidth: Float
        ): Boolean {
            val angle = p.angle(pp)

            val sina = sin(angle)
            val cosa = -cos(angle)

            val sin = lineWidth * sina
            val cos = lineWidth * cosa

            /*var dpp = pp.x - p.x
            if (dpp == 0.0f) {
                dpp = 0.001f
            }
            val k = (pp.y - p.y) / dpp
*/
            val mPointLeftTop = PointF().apply {
                set(
                    p.x + cos,
                    p.y + sin
                )
            }

            val mPointLeftBottom = PointF().apply {
                set(
                    pp.x + cos,
                    pp.y + sin
                )
            }

            val mPointRightTop = PointF().apply {
                set(
                    p.x + -cos,
                    p.y + -sin
                )
            }

            val mPointRightBottom = PointF().apply {
                set(
                    pp.x + -cos,
                    pp.y + -sin
                )
            }

            val minMaxX = minMax(
                mPointLeftTop.x,
                mPointLeftBottom.x,
                mPointRightTop.x,
                mPointRightBottom.x
            )

            val minMaxY = minMax(
                mPointLeftTop.y,
                mPointLeftBottom.y,
                mPointRightTop.y,
                mPointRightBottom.y
            )

            if (
                x < minMaxX.first ||
                x > minMaxX.second ||
                y < minMaxY.first ||
                y > minMaxY.second
            ) {
                return false
            }

            return poly(
                x,
                y,
                arrayListOf(
                    mPointLeftTop,
                    mPointRightTop,
                    mPointRightBottom,
                    mPointLeftBottom
                )
            )

            /*val y1 = k * (x - mPointLeftTop.x) + mPointLeftTop.y
            val y2 = k * (x - mPointRightTop.x) + mPointRightTop.y

            return (y1 > y && y > y2) || (y1 < y && y < y2)*/
        }
    }
}