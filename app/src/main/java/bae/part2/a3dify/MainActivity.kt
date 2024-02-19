package bae.part2.a3dify

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import bae.part2.a3dify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()

        binding.sampleText.setOnClickListener {
            val intent = Intent(this, ThreedActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * A native method that is implemented by the 'a3dify' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'a3dify' library on application startup.
        init {
            System.loadLibrary("a3dify")
        }
    }
}