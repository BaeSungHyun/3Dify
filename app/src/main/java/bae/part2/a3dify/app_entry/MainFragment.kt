package bae.part2.a3dify.app_entry

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import bae.part2.a3dify.databinding.FragmentMainBinding
import bae.part2.a3dify.threed_graphic.ThreedActivity

class MainFragment : Fragment() {
    private lateinit var binding : FragmentMainBinding

    private val imageLoadLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        uriList ->  // List<Uri>!

        // Get images to here and show it as slide ? or UI ? So that user can choose.
        // Need to modify UI.
        // Need to save this image URIs as application data. SharedPreference?
    }

    internal val requestReadExternalStorageLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted : Boolean ->
        if (isGranted) {
            loadImage()
        } else {
            Toast.makeText(context, "Image not accessible.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        binding.gallery.setOnClickListener {
            checkPermission()
        }

        binding.threed.setOnClickListener {
            val intent = Intent(context, ThreedActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    internal fun loadImage() {
        imageLoadLauncher.launch("image/*")
    }
}