package com.intern.conjob.ui.home.matching.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.intern.conjob.arch.extensions.convertDpToPx
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.arch.util.FileType
import com.intern.conjob.data.model.Post
import com.intern.conjob.databinding.ItemPostFileBinding

class PostFileAdapter : RecyclerView.Adapter<PostFileAdapter.ListBannerHolder>() {
    var posts: List<Post> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListBannerHolder =
        ListBannerHolder(
            ItemPostFileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = posts.size

    override fun onBindViewHolder(holder: ListBannerHolder, position: Int) {
        holder.bindView(posts[position])
    }

    class ListBannerHolder(private val binding: ItemPostFileBinding) :
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
                    val player = ExoPlayer.Builder(itemView.context).build()
                    player.addMediaItem(MediaItem.fromUri(Uri.parse(post.url)))
                    videoPost.player = player
                }
            }
        }
    }
}
