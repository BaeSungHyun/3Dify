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
import androidx.recyclerview.widget.GridLayoutManager
import com.part2.a3dify.app_entry.image_recycler_view.ImageAdapter
import com.part2.a3dify.app_entry.image_recycler_view.ImageDataClass
import com.part2.a3dify.databinding.FragmentMainBinding
import com.part2.a3dify.threed_graphic.ThreedActivity

class MainFragment : Fragment() {
    private lateinit var binding : FragmentMainBinding
    private lateinit var imageAdapter: ImageAdapter

    private val imageLoadLauncher = registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        uriList ->  // List<Uri>!
        updateImages(uriList)
        // Currently from Gallery, but in future it will get images (history) from backend
    }

    private fun updateImages(uriList : List<Uri>) {
        val images = uriList.map{ImageDataClass(
            it,
            configureBitmap(it)
            )}
        imageAdapter.changeImageGroup(images)
    }

    private fun configureBitmap(uri: Uri): Bitmap {
        var bitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
        val matrix = Matrix()
        matrix.postRotate(90.0f)
        bitmap = Bitmap.createScaledBitmap(bitmap, 1000, 1000, true)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
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
        imageAdapter = ImageAdapter(requireContext())

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