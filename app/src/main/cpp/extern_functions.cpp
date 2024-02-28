#include "gles3jni.h"
#include <jni.h>
#include <cstring>

static void printGLString(const char* name, GLenum s) {
    const char* v = (const char*) glGetString(s);
    ALOGV("GL %s: %s\n", name, v);
}

// -------------------------------------
static Renderer* g_renderer = NULL;

unsigned int Shader::total = 0;

extern "C" {
JNIEXPORT void JNICALL Java_bae_part2_a3dify_threed_1graphic_GraphicsLib_init(JNIEnv* env, jobject obj);
JNIEXPORT void JNICALL Java_bae_part2_a3dify_threed_1graphic_GraphicsLib_resize(JNIEnv* env, jobject obj, jint width, jint height);
JNIEXPORT void JNICALL Java_bae_part2_a3dify_threed_1graphic_GraphicsLib_step(JNIEnv* env, jobject obj);
};

extern "C" JNIEXPORT void JNICALL Java_bae_part2_a3dify_threed_1graphic_GraphicsLib_init(JNIEnv* env, jobject obj) {
    if (g_renderer) {
        delete g_renderer;
        g_renderer = NULL;
    }

    printGLString("Version", GL_VERSION);
    printGLString("Vendor", GL_VENDOR);
    printGLString("Renderer", GL_RENDERER);
    printGLString("Extensions", GL_EXTENSIONS);

    const char* versionStr = (const char*)glGetString(GL_VERSION);
    if (strstr(versionStr, "OpenGL ES 3.")) {
        g_renderer = createES3Renderer();
    } else { // nullptr
        ALOGE("Unsupported OpenGL ES version");
    }
}

extern "C" JNIEXPORT void JNICALL Java_bae_part2_a3dify_threed_1graphic_GraphicsLib_resize(JNIEnv* env, jobject obj, jint width, jint height) {
    if (g_renderer) {
        g_renderer->resize(width, height);
    }
}

extern "C" JNIEXPORT void JNICALL Java_bae_part2_a3dify_threed_1graphic_GraphicsLib_step(JNIEnv* env, jobject obj) {
    if (g_renderer) {
        g_renderer->render();
    }
}