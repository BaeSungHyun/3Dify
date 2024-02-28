//
// Created by TFG5576XG on 2024-02-28.
//

#ifndef INC_3DIFY_LOG_ANDROID_H
#define INC_3DIFY_LOG_ANDROID_H

#include <android/log.h>

#define LOG_TAG "3DIFY"
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#if DEBUG
#define ALOGV(...) \
    __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)
#else
#define ALOGV(...)
#endif

#endif //INC_3DIFY_LOG_ANDROID_H
