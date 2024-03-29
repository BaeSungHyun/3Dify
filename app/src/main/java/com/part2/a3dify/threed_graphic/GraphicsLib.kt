package com.part2.a3dify.threed_graphic

public class GraphicsLib {
    external fun stringFromJNI(): String
    external fun init()
    external fun resize(width : Int, height: Int)
    external fun step()

    companion object {
        init {
            System.loadLibrary("a3dify")
        }
    }
}