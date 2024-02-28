#include "shader.h"
#include "include_gl.h"
#include "log_android.h"
#include <type_traits>


Shader::Shader(const char* vShaderCode, const char* fShaderCode) {
    ID = total++;
    // 2. compile shaders
    unsigned int vertex, fragment;
    int success;
    char infoLog[512];

    // vertex shader
    vertex = glCreateShader(GL_VERTEX_SHADER);
    glShaderSource(vertex, 1, &vShaderCode, nullptr);
    glCompileShader(vertex);
    // print compile errors if any
    glGetShaderiv(vertex, GL_COMPILE_STATUS, &success);
    if (!success) {
        glGetShaderInfoLog(vertex, 512, nullptr, infoLog);
        std::ostringstream str;
        str << "ERROR::SHADER::VERTEX::COMPILATION_FAILED\n" << infoLog << "\n";
        throw std::runtime_error(str.str());
    }

    // fragment shader
    fragment = glCreateShader(GL_FRAGMENT_SHADER);
    glShaderSource(fragment, 1, &fShaderCode, nullptr);
    glCompileShader(fragment);
    // print compile errors if any
    glGetShaderiv(vertex, GL_COMPILE_STATUS, &success);
    if (!success) {
        glGetShaderInfoLog(vertex, 512, nullptr, infoLog);
        std::ostringstream str;
        str << "ERROR::SHADER::FRAGMENT::COMPILATION_FAILED\n" << infoLog << "\n";
        throw std::runtime_error(str.str());
    }

    // shader program
    ID = glCreateProgram();
    glAttachShader(ID, vertex);
    glAttachShader(ID, fragment);
    glLinkProgram(ID);

    // print linking errors if any
    glGetProgramiv(ID, GL_LINK_STATUS, &success);
    if (!success) {
        glGetProgramInfoLog(ID, 512, nullptr, infoLog);
        std::ostringstream str;
        str << "ERROR::SHADER::PROGRAM::LINKING_FAILED\n" << infoLog << "\n";
        throw std::runtime_error(str.str());
    }

    // delete shaders; already linked to the program
    glDeleteShader(vertex);
    glDeleteShader(fragment);
}

Shader::~Shader() {
    glDeleteProgram(ID);
}

void Shader::use() {
    glUseProgram(ID);
}

template<typename T>
void Shader::set(const std::string& name, T value) const {
    if (std::is_same<T, bool>::value || std::is_same<T, int>::value ) { // :: way to access static member function
        glUniform1i(glGetUniformLocation(ID, name.c_str()), (int)value);
    }

    if (std::is_same<T, float>::value) {
        glUniform1f(glGetUniformLocation(ID, name.c_str()), value);
    }
}
