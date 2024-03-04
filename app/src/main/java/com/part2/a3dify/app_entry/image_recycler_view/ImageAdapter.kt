package com.part2.a3dify.app_entry.image_recycler_view

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.part2.a3dify.databinding.ViewholderImageBinding

class ImageAdapter(private val context: Context) : RecyclerView.Adapter<ImageViewHolder>() {
    inner class DiffUtilCallback() : DiffUtil.ItemCallback<ImageDataClass>() {
        override fun areItemsTheSame(
            oldItem: ImageDataClass,
            newItem: ImageDataClass
        ): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(
            oldItem: ImageDataClass,
            newItem: ImageDataClass
        ): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncDiffer = AsyncListDiffer(this, DiffUtilCallback())
    private var imagesGroup : List<ImageDataClass> = emptyList() // Preventing possible memory leak

    fun changeImageGroup(_imagesGroup : List<ImageDataClass>) {
        imagesGroup = _imagesGroup
        asyncDiffer.submitList(imagesGroup)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ViewholderImageBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding, context)
    }

    override fun getItemCount(): Int {
        return imagesGroup.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imagesGroup[position])
    }
}