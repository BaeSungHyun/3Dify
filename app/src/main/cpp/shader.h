#ifndef SHADER_H
#define SHADER_H

#include <string>
#include <fstream> // file stream
#include <sstream> // string stream
#include <iostream> // input output stream

class Shader {
public:
    static unsigned int total;

    // Program ID
    unsigned int ID;

    // constructor reads and builds the shader
    Shader(const char* vShaderCode, const char* fShadercode);
    ~Shader();

    // use/activate the shader
    void use();

    // utility functions for setting uniform variable in GLSL
    template <typename T>
    void set(const std::string& name, T value) const;
private:


};

#endif