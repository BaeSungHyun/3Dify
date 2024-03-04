package com.part2.a3dify.app_entry.image_recycler_view

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.part2.a3dify.databinding.ViewholderImageBinding

// RecyclerView.ViewHolder sets data member 'itemView' to the 'binding.root'
class ImageViewHolder(private val binding: ViewholderImageBinding, private val context: Context) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : ImageDataClass) {
        binding.previewImageView.setImageURI(item.uri)
//        Modern Method, but not quite good for RecyclerView which doesn't have context
//        binding.previewImageView.setImageBitmap(context.contentResolver.loadThumbnail(
//            item.uri,
//            Size(binding.previewImageView.width, binding.previewImageView.height), null)
//        )
        binding.previewImageView.setImageBitmap(item.bitmapMini)
    }
}