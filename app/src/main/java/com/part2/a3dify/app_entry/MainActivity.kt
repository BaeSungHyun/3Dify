package com.part2.a3dify.app_entry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.part2.a3dify.R
import com.part2.a3dify.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ######### Doesn't need this because it's inside FrameLayout now ##########
        // Adding toolbar support
        // binding.mainToolbar.title = "3Dify"
        // val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        // setSupportActionBar(toolbar)
        // How to set supportActionBar
//
//        Log.d("Cache Directory", baseContext.filesDir.toString()) /data/user/0/com.part2.a3dify/files

        if (savedInstanceState == null) { // to ensure that the fragment is added only once
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<InitialFragment>(R.id.fragment)
            }
        }
    }
}