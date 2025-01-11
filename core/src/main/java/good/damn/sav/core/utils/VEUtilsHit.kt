package good.damn.sav.core.utils

import android.graphics.Point
import android.graphics.PointF
import good.damn.sav.misc.extensions.angle
import good.damn.sav.misc.extensions.minMax
import kotlin.math.cos
import kotlin.math.sin

class VEUtilsHit {
    companion object {
        inline fun checkLine(
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

            var dpp = pp.x - p.x
            if (dpp == 0.0f) {
                dpp = 0.001f
            }
            val k = (pp.y - p.y) / dpp

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

            val y1 = k * (x - mPointLeftTop.x) + mPointLeftTop.y
            val y2 = k * (x - mPointRightTop.x) + mPointRightTop.y

            return (y1 > y && y > y2) || (y1 < y && y < y2)
        }
    }
}