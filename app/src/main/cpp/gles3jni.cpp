//
// Created by TFG5576XG on 2024-02-23.
//

#include "gles3jni.h"
#include <jni.h>


bool checkGLError(const char* funcName) {
    GLint err = static_cast<GLint>(glGetError());
    if (err != GL_NO_ERROR) {
        ALOGE("GL error after %s(): 0x%08x\n", funcName, err);
        return true;
    }
    return false;
}

// ------------------------ Renderer Class -----------------------
Renderer::Renderer()
{ }

Renderer::~Renderer() {
}

void Renderer::resize(int w, int h) {
    glViewport(0, 0, w, h);
}

void Renderer::render() {
    glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    checkGLError("Renderer::render");
}

