package bae.part2.a3dify.app_entry

import android.content.Intent
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import bae.part2.a3dify.app_entry.image_recycler_view.ImageAdapter
import bae.part2.a3dify.app_entry.image_recycler_view.ImageViewHolder
import bae.part2.a3dify.databinding.FragmentMainBinding
import bae.part2.a3dify.threed_graphic.ThreedActivity

class MainFragment : Fragment() {
    private lateinit var binding : FragmentMainBinding
    private lateinit var imageAdapter: ImageAdapter

    private val imageLoadLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        uriList ->  // List<Uri>!
        updateImages(uriList)
        // Currently from Gallery, but in future it will get images (history) from backend
    }

    private fun updateImages(uriList : List<Uri>) {
        val images = uriList.map{ImageViewHolder.ImageInfo(it)}
        imageAdapter.changeImageGroup(images)
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
        binding = FragmentMainBinding.inflate(inflater)
        imageAdapter = ImageAdapter()

        binding.gallery.setOnClickListener {
            checkPermission()
        }

        binding.threed.setOnClickListener {
            val intent = Intent(context, ThreedActivity::class.java)
            startActivity(intent)
        }

        binding.imageRecyclerView.apply {
            adapter = imageAdapter
            layoutManager = GridLayoutManager(context, 4)
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