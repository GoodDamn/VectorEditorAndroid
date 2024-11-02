LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := VEByteArrayUtilsCpp
LOCAL_SRC_FILES := VEByteArrayUtilsCpp.cpp
#LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog

include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)

LOCAL_MODULE    := VEUtilsIntCpp
LOCAL_SRC_FILES := VEUtilsIntCpp.cpp
#LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog

include $(BUILD_SHARED_LIBRARY)