LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := VEUtilsByteArray
LOCAL_SRC_FILES := VEUtilsByteArray.cpp
#LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := VEUtilsInt
LOCAL_SRC_FILES := VEUtilsInt.cpp
#LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog
include $(BUILD_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := VEUtilsFloat
LOCAL_SRC_FILES := VEUtilsFloat.cpp
#LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog
include $(BUILD_SHARED_LIBRARY)