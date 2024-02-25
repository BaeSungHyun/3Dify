package bae.part2.a3dify

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