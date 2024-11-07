#include "jni.h"
//#include "android/log.h"

extern "C"
JNIEXPORT void JNICALL Java_good_damn_sav_misc_utils_VEUtilsByteArrayJava_interpolate(
    JNIEnv* env,
    jclass thiz,
    jbyteArray v,
    jbyteArray vv,
    jfloat t,
    jbyteArray to
) {
    jsize vSize = env->GetArrayLength(v);

    jbyte* vElements = env->GetByteArrayElements(
        v,
        nullptr
    );

    jbyte* vvElements = env->GetByteArrayElements(
        vv,
        nullptr
    );

    jbyte* toElements = env->GetByteArrayElements(
        to,
        0
    );

    float fromVal;
    float toVal;

    for (int i = 0; i < vSize; i++) {
        fromVal = static_cast<unsigned char>(vElements[i]);
        toVal = static_cast<unsigned char>(vvElements[i]);
        toElements[i] = (jbyte) (
            fromVal + (toVal - fromVal) * t
        );
    }

    env->ReleaseByteArrayElements(
        to,
        toElements,
        0
    );
}