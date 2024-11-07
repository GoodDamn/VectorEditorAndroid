//
// Created by gooddamn on 02.11.2024.
//
#include "jni.h"

extern "C"
JNIEXPORT jint JNICALL
Java_good_damn_sav_misc_utils_VEUtilsIntJava_int32___3BI(
    JNIEnv* env,
    jclass self,
    jbyteArray n,
    jint offset
) {
    auto* nElements = reinterpret_cast<
        unsigned char*
    >(env->GetByteArrayElements(
        n,
        nullptr
    ));

    return nElements[offset] << 24 |
        nElements[offset + 1] << 16 |
        nElements[offset + 2] << 8 |
        nElements[offset + 3];
}

extern "C"
JNIEXPORT jbyteArray JNICALL
Java_good_damn_sav_misc_utils_VEUtilsIntJava_int32__I(
    JNIEnv* env,
    jclass self,
    jint n
) {
    jbyteArray data = env->NewByteArray(4);
    jbyte fill[4] {
        static_cast<jbyte>(n >> 24),
        static_cast<jbyte>(n >> 16 & 0xff),
        static_cast<jbyte>(n >> 8 & 0xff),
        static_cast<jbyte>(n & 0xff)
    };

    env->SetByteArrayRegion(
        data,
        0,
        4,
        fill
    );

    return data;
}