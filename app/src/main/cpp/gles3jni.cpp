//
// Created by TFG5576XG on 2024-02-23.
//

#include "gles3jni.h"

#include <jni.h>
#include <cstring>

bool checkGLError(const char* funcName) {
    GLint err = static_cast<GLint>(glGetError());
    if (err != GL_NO_ERROR) {
        ALOGE("GL error after %s(): 0x%08x\n", funcName, err);
        return true;
    }
    return false;
}

GLuint createShader(GLenum shaderType, const char* src) {
    GLuint shader = glCreateShader(shaderType);
    if (!shader) {
        checkGLError("glCreateShader");
        return 0;
    }
    glShaderSource(shader, 1, &src, NULL);

    GLint compiled = GL_FALSE;

    glCompileShader(shader);
    glGetShaderiv(shader, GL_COMPILE_STATUS, &compiled);

    if (!compiled) {
        GLint infoLogLen = 0;
        glGetShaderiv(shader, GL_INFO_LOG_LENGTH, &infoLogLen);
        if (infoLogLen > 0) {
            GLchar* infoLog = new GLchar[infoLogLen];
            if (infoLog) {
                glGetShaderInfoLog(shader, infoLogLen, NULL, infoLog);
                ALOGE("Could not compile %s shader:\n%s\n",
                      shaderType == GL_VERTEX_SHADER ? "vertex" : "fragment", infoLog);
                delete[] infoLog;
            }
        }
        glDeleteShader(shader);
        return 0;
    }
    return shader;
}

GLuint createProgram(const char* vtxSrc, const char* fragSrc) {
    GLuint vtxShader {0};
    GLuint fragShader {0};
    GLuint program {0};
    GLint linked {GL_FALSE};

    vtxShader = createShader(GL_VERTEX_SHADER, vtxSrc);
    if (!vtxShader) {
        glDeleteShader(vtxShader);
        return program;
    }

    fragShader = createShader(GL_FRAGMENT_SHADER, fragSrc);
    if (!fragShader) {
        glDeleteShader(fragShader);
        return program;
    }

    program = glCreateProgram();
    if (!program) {
        checkGLError("glCreateProgram");
        glDeleteShader(vtxShader);
        glDeleteShader(fragShader);
        return program;
    }

    glAttachShader(program, vtxShader);
    glAttachShader(program, fragShader);

    glLinkProgram(program);
    glGetProgramiv(program, GL_LINK_STATUS, &linked);

    if (!linked) {
        ALOGE("Could not link program");
        GLint infoLogLen = 0;

        glGetProgramiv(program, GL_INFO_LOG_LENGTH, &infoLogLen);
        if (infoLogLen) {
            GLchar* infoLog = new GLchar[infoLogLen];
            if (infoLog) {
                glGetProgramInfoLog(program, infoLogLen, NULL, infoLog);
                ALOGE("Could not link program:\n%s\n", infoLog);

                delete[] infoLog;
            }
        }
        glDeleteProgram(program);
        program = 0;
    }

    glDeleteShader(vtxShader);
    glDeleteShader(fragShader);
    return program;
}

static void printGLString(const char* name, GLenum s) {
    const char* v = (const char*) glGetString(s);
    ALOGV("GL %s: %s\n", name, v);
}

// ------------------------ Renderer Class -----------------------
Renderer::Renderer() {

}

Renderer::~Renderer() {}

void Renderer::resize(int w, int h) {
    glViewport(0, 0, w, h);
}

void Renderer::render() {
    glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    checkGLError("Renderer::render");
}

// -------------------------------------
static Renderer* g_renderer = NULL;

extern "C" {
    JNIEXPORT void JNICALL Java_bae_part2_a3dify_GraphicsLib_init(JNIEnv* env, jobject obj);
    JNIEXPORT void JNICALL Java_bae_part2_a3dify_GraphicsLib_resize(JNIEnv* env, jobject obj, jint width, jint height);
    JNIEXPORT void JNICALL Java_bae_part2_a3dify_GraphicsLib_step(JNIEnv* env, jobject obj);
};

extern "C" JNIEXPORT void JNICALL Java_bae_part2_a3dify_GraphicsLib_init(JNIEnv* env, jobject obj) {
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
    } else {
        ALOGE("Unsupported OpenGL ES version");
    }
}

extern "C" JNIEXPORT void JNICALL Java_bae_part2_a3dify_GraphicsLib_resize(JNIEnv* env, jobject obj, jint width, jint height) {
    if (g_renderer) {
        g_renderer->resize(width, height);
    }
}

extern "C" JNIEXPORT void JNICALL Java_bae_part2_a3dify_GraphicsLib_step(JNIEnv* env, jobject obj) {
    if (g_renderer) {
        g_renderer->render();
    }
}