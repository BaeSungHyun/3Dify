package com.part2.a3dify.threed_graphic

import android.annotation.SuppressLint
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.part2.a3dify.databinding.ActivityThreedBinding

class ThreedActivity : AppCompatActivity() {
    private lateinit var binding : ActivityThreedBinding
//    private lateinit var graphicsView: GraphicsView
    private lateinit var renderer : GraphicsRenderer


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityThreedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        renderer = GraphicsRenderer()

        binding.openglView.setEGLConfigChooser(8, 8, 8, 0, 8, 0)
        binding.openglView.setEGLContextClientVersion(3)
        binding.openglView.setRenderer(renderer)
        binding.openglView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY // only when there is change

        binding.openglView.setOnTouchListener { _, motionEvent ->
            // C++ code
            when (motionEvent.action) {
                MotionEvent.ACTION_MOVE -> {
                    // ui thread..?
                    Log.d("position", "${motionEvent.x} - ${motionEvent.y}")
                }
            }
            return@setOnTouchListener true
        }
    }
}

