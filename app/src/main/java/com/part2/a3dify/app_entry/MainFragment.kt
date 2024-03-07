package com.part2.a3dify.app_entry

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.part2.a3dify.app_containers.MainContainer
import com.part2.a3dify.app_containers.MainFragmentContainer
import com.part2.a3dify.app_containers.ThrDifyApplication
import com.part2.a3dify.app_entry.image_recycler_view.ImageAdapter
import com.part2.a3dify.app_entry.image_recycler_view.ImageDataClass
import com.part2.a3dify.app_entry.viewmodels.MainFragmentViewModel
import com.part2.a3dify.databinding.FragmentMainBinding
import com.part2.a3dify.threed_graphic.ThreedActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private lateinit var binding : FragmentMainBinding
    private lateinit var imageAdapter : ImageAdapter
    private val viewModel : MainFragmentViewModel by viewModels {
        var temp : MainFragmentContainer? = (requireActivity().application as ThrDifyApplication).mainContainer.mainFragmentContainer
        if (temp == null) {
            temp = MainFragmentContainer()
            return@viewModels temp.mainFragmentViewModelFactory
        }
        else
            return@viewModels temp.mainFragmentViewModelFactory
    }

    private val imageLoadLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        uriList ->  // List<Uri>!
        viewModel.updateRecyclerImages(uriList)
        // Currently from Gallery, but in future it will get images (history) from backend
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
        imageAdapter = ImageAdapter(requireContext())

        binding = FragmentMainBinding.inflate(inflater)

        binding.progressBarRecycler.hide()

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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState->
                    Log.d("coroutine", "MainFragment")
                    if (uiState.showLoading == true) binding.progressBarRecycler.show()
                    else binding.progressBarRecycler.hide()  // includes false, null

                    if (uiState.uriList == null) return@collect
                    imageAdapter.changeImageGroup(uiState.uriList) // only invoked when collected
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MainFragment", "OnViewCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("MainFragment", "OnViewStateRestored")
    }

    // Coming back from Gallery invokes this method. -- meaning
    // Coroutine repeatOnLifecycle(Start) should be called. Or Resumed?
    override fun onStart() {
        super.onStart()
        Log.d("MainFragment", "OnStart")
    }

    // Need to check on memory when working on 3D task and delete viewModel
    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity().application as ThrDifyApplication).mainContainer.mainFragmentContainer = null
    }

    internal fun loadImage() {
        imageLoadLauncher.launch("image/*") // Specifying for images
    }

//    internal fun fetchImages(context: Context): List<ImageDataClass> {
//        Log.d("fetchImages", "Invoked")
//        val imageList = mutableListOf<ImageDataClass>()
//
//        val projection = arrayOf(
//            MediaStore.Images.Media._ID,
//            MediaStore.Images.Media.SIZE
//        )
//
//        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
//
//        context.contentResolver.query(
//            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//            projection,
//            null, // No selection
//            null, // No selection args
//            sortOrder
//        )?.use {cursor ->
//            // Cache column indices so that you don't need to call 'getColumnIndexOrThrow()' each time
//            // you process a row from the query result.
//            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
//            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
//
//            while (cursor.moveToNext()) {
//                val id = cursor.getLong(idColumn)
//                val size = cursor.getInt(sizeColumn)
//
//                val contentUri: Uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)
//
//                val thumbnailUri = MediaStore.Images.Thumbnails.getThumbnail

//                imageList += ImageDataClass(contentUri)
//            }
//        }
//        return imageList
//    }
}