package com.intern.conjob.ui.home.matching.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.intern.conjob.arch.extensions.convertDpToPx
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.arch.util.FileType
import com.intern.conjob.arch.util.VideoPlayer
import com.intern.conjob.data.model.Post
import com.intern.conjob.databinding.ItemPostFileBinding

class PostFileAdapter : RecyclerView.Adapter<PostFileAdapter.FileViewHolder>() {
    var posts: List<Post> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder =
        FileViewHolder(
            ItemPostFileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bindView(posts[position])
    }

    override fun onViewAttachedToWindow(holder: FileViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.addPlayer()
    }

    override fun onViewDetachedFromWindow(holder: FileViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.removePlayer()
    }

    class FileViewHolder(private val binding: ItemPostFileBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val imageThumbnailSize = itemView.context.convertDpToPx(Constants.IMAGE_THUMBNAIL_SIZE)

        fun bindView(post: Post) {
            binding.apply {
                if (post.type == FileType.IMAGE.type) {
                    imgPost.visibility = View.VISIBLE
                    Glide.with(itemView.context).load(post.url).override(imageThumbnailSize)
                        .fitCenter().into(imgPost)
                } else {
                    videoPost.visibility = View.VISIBLE
                }
            }
        }

        fun addPlayer() {
            binding.videoPost.player = VideoPlayer.player
        }

        fun removePlayer() {
            binding.videoPost.player = null
        }
    }
}
