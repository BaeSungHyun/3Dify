package bae.part2.a3dify.app_entry

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.add
import androidx.fragment.app.commit
import bae.part2.a3dify.R
import bae.part2.a3dify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Adding toolbar support
        binding.mainToolbar.title = "3Dify"
        setSupportActionBar(binding.mainToolbar)
        // How to set supportActionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        if (savedInstanceState == null) { // to ensure that the fragment is added only once
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<InitialFragment>(R.id.fragment)
            }
        }
    }
}