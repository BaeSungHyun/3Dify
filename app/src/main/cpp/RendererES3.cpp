//
// Created by TFG5576XG on 2024-02-25.
//

#include <EGL/egl.h>
#include "Renderer.h"
#include "ShaderSourceCode.h"


class RendererES3 : public Renderer {
public:
    RendererES3(const char* vertexPath, const char* fragmentPath);
    virtual ~RendererES3();

private:
    const EGLContext mEglContext;
    Shader shaderProgram;


};

Renderer* createES3Renderer() {
    try {
        auto* renderer = new RendererES3(VERTEX_SHADER, FRAGMENT_SHADER);
        return renderer;
    } catch (const std::runtime_error& e) {
        ALOGE("%s", e.what()); // WHY ???
        return nullptr;
    }
}

RendererES3::RendererES3(const char* vertexPath, const char* fragmentPath)
    : mEglContext(eglGetCurrentContext()), shaderProgram(vertexPath, fragmentPath) {
    // 1. EGL context (similar to glad I think. Since OpenGL (ES) is only specification,
    //      need API from vendor. In here, Android.
    // 2. Shader Program to use. Currently only one, because we need only one shader.
}

RendererES3::~RendererES3() {
    /* The destructor may be called after the context has already been
   * destroyed, in which case our objects have already been destroyed.
   *
   * If the context exists, it must be current. This only happens when we're
   * cleaning up after a failed init().
   */
    shaderProgram.~Shader();
    if (eglGetCurrentContext() != mEglContext) return;
}