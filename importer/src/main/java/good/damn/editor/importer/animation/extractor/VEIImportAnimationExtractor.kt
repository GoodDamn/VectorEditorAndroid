package good.damn.editor.importer.animation.extractor

import good.damn.sav.misc.extensions.io.readFraction
import java.io.InputStream

interface VEIImportAnimationExtractor<
    KEYFRAME,
    INTERPOLATOR
> {

    companion object {
        fun <KEYFRAME, INTERPOLATOR> extractAnimation(
            keyframesCount: Int,
            inp: InputStream,
            extractor: VEIImportAnimationExtractor<
                KEYFRAME,
                INTERPOLATOR
            >
        ) = ArrayList<INTERPOLATOR>(
            keyframesCount - 1
        ).apply {
            var start: KEYFRAME? = null
            var end: KEYFRAME
            for (j in 0 until keyframesCount) {
                if (start == null) {
                    start = extractor.createKeyframe(
                        inp,
                        inp.readFraction()
                    )
                    continue
                }

                end = extractor.createKeyframe(
                    inp,
                    inp.readFraction()
                )

                add(
                    extractor.createInterpolator(
                        start,
                        end
                    )
                )

                start = end
            }
        }
    }

    fun createKeyframe(
        stream: InputStream,
        factor: Float
    ): KEYFRAME

    fun createInterpolator(
        start: KEYFRAME,
        end: KEYFRAME
    ): INTERPOLATOR
}