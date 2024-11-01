#include "jni.h"

extern "C"
JNIEXPORT void JNICALL Java_good_damn_sav_misc_utils_VEUtilsByteArrayJava_interpolate(
    JNIEnv* env,
    jclass thiss,
    jbyteArray v,
    jbyteArray vv,
    jfloat t,
    jbyteArray to
) {
    jsize vSize = env->GetArrayLength(v);

    if (vSize != env->GetArrayLength(vv)
        || vSize != env->GetArrayLength(to)
    ) {
        return;
    }

    jbyte* vElements = env->GetByteArrayElements(
        v,
        0
    );

    jbyte* vvElements = env->GetByteArrayElements(
        vv,
        0
    );

    jbyte* toElements = env->GetByteArrayElements(
        to,
        0
    );

    unsigned char fromVal;
    unsigned char toVal;

    for (int i = 0; i < vSize; i++) {
        fromVal = *(vElements + i);
        toVal = *(vvElements + i);
        *(toElements + i) = fromVal + (toVal - fromVal) * t;
    }
}