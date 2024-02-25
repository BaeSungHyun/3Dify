//
// Created by TFG5576XG on 2024-02-22.
//

#ifndef INC_3DIFY_GLES3JNI_H
#define INC_3DIFY_GLES3JNI_H

#include <android/log.h>
#include <math.h>

#if __ANDROID_API__ >= 24
#include <GLES3/gl32.h>
#else
#include <GLES3/gl31.h>
#endif

#define DEBUG 1

#define LOG_TAG "3DIFY"
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#if DEBUG
#define ALOGV(...) \
    __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)
#else
#define ALOGV(...)
#endif

// Three Coordinate Spaces in OpenGL Android (Mobile)
// 1. Model : [-1 .. 1] x [-1 .. 1] Space
// 2. Scene
//      - landscape : [-1 .. 1] x [ -1 / (2 * w / h) .. 1 / (2 * w / h) ]
//      - portrait : [ -1 / (2 * h / w) ..  1 / (2 * h / w) ] x [-1 .. 1]
// 3. Clip : [-1 .. 1] x [-1 .. 1] Space

struct Vertex {
    GLfloat pos[2];
    GLubyte rgba[4];
};
extern const Vertex QUAD[4];

// returns true if a GL error occurred
extern bool checkGLError(const char* funcName);
extern GLuint createShader(GLenum shaderType, const char* src);
extern GLuint createProgram(const char* vtxSrc, const char* fragSrc);

class Renderer {
public:
    virtual ~Renderer();
    void resize(int w, int h);
    void render();

protected:
    Renderer();

private:

};

extern Renderer* createES3Renderer();

#endif //INC_3DIFY_GLES3JNI_H
