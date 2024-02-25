import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

import android.opengl.GLES32
import android.opengl.GLSurfaceView
import bae.part2.a3dify.GraphicsLib

class GraphicsRenderer : GLSurfaceView.Renderer {
    val TAG: String = "GLES3JNI"
    val DEBUG = true
    val libLoader : GraphicsLib = GraphicsLib()

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig) {
        libLoader.init()
    }

    override fun onDrawFrame(unused: GL10) {
        libLoader.step()
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        libLoader.resize(width, height)
    }
}