package bae.part2.a3dify.app_entry.image_recycler_view

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import bae.part2.a3dify.databinding.ViewholderImageBinding

// RecyclerView.ViewHolder sets data member 'itemView' to the 'binding.root'
class ImageViewHolder(private val binding: ViewholderImageBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item : ImageInfo) {
        binding.previewImageView.setImageURI(item.uri)
    }

    data class ImageInfo(val uri : Uri)
}