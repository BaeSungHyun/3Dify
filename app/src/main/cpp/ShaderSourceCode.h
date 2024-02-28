//
// Created by TFG5576XG on 2024-02-28.
//

#ifndef INC_3DIFY_SHADERSOURCECODE_H
#define INC_3DIFY_SHADERSOURCECODE_H

#define STR(s) #s
#define STRV(s) STR(s)

#define POS_ATTRIB 0
#define COLOR_ATTRIB 1

static const char* VERTEX_SHADER =
        "#version 300 es\n"
        "layout(location = " STRV(POS_ATTRIB) ") in vec3 pos;\n"
        "layout(location=" STRV(COLOR_ATTRIB) ") in vec4 color;\n"
        "out vec4 vColor;\n"
        "uniform mat4 model;\n"
        "uniform mat4 view;\n"
        "uniform mat4 projection;\n"
        "uniform mat4 camera;\n"
        "void main() {\n"
        "    gl_Position = vec4(pos, 1.0);\n"
        "    vColor = color;\n"
        "}\n";

static const char* FRAGMENT_SHADER =
        "#version 300 es\n"
        "precision mediump float;\n"
        "in vec4 vColor;\n"
        "out vec4 outColor;\n"
        "void main() {\n"
        "    outColor = vColor;\n"
        "}\n";

#endif //INC_3DIFY_SHADERSOURCECODE_H
