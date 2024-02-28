#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_bae_part2_a3dify_threed_1graphic_GraphicsLib_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
