package bae.part2.a3dify.app_entry.image_recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import bae.part2.a3dify.databinding.ViewholderImageBinding

class ImageAdapter : RecyclerView.Adapter<ImageViewHolder>() {
    inner class DiffUtilCallback() : DiffUtil.ItemCallback<ImageViewHolder.ImageInfo>() {
        override fun areItemsTheSame(
            oldItem: ImageViewHolder.ImageInfo,
            newItem: ImageViewHolder.ImageInfo
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ImageViewHolder.ImageInfo,
            newItem: ImageViewHolder.ImageInfo
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncDiffer = AsyncListDiffer(this, DiffUtilCallback())
    private var imagesGroup : List<ImageViewHolder.ImageInfo> = emptyList() // Preventing possible memory leak

    fun changeImageGroup(_imagesGroup : List<ImageViewHolder.ImageInfo>) {
        imagesGroup = _imagesGroup
        asyncDiffer.submitList(imagesGroup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ViewholderImageBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return imagesGroup.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imagesGroup[position])
    }
}