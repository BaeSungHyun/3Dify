//
// Created by TFG5576XG on 2024-02-22.
//

#ifndef INC_3DIFY_GLES3JNI_H
#define INC_3DIFY_GLES3JNI_H

#include <math.h>

#include "include_gl.h"
#include "shader.h"
#include "log_android.h"

#define DEBUG 1

// returns true if a GL error occurred
extern bool checkGLError(const char* funcName);

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
