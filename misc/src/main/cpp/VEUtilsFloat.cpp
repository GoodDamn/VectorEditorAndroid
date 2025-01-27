//
// Created by gooddamn on 02.11.2024.
//
#include "jni.h"
#define LEN_COLOR 4

extern "C"
JNIEXPORT jbyte JNICALL
Java_good_damn_sav_misc_utils_VEUtilsFloatJava_getDigitalFraction(
    JNIEnv* env,
    jclass self,
    float fraction
) {
    return static_cast<jbyte>(
        fraction * 255
    );
}

extern "C"
JNIEXPORT jbyte JNICALL
Java_good_damn_sav_misc_utils_VEUtilsFloatJava_toDigitalFraction(
    JNIEnv* env,
    jclass self,
    float fraction,
    float divider
) {
    return static_cast<jbyte>(
        fraction / divider * 255
    );
}