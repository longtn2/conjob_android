package com.intern.conjob.ui.home.matching.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
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
        set(value) {
            field = value
            notifyItemRangeInserted(1, value.size)
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
        holder.bindView(posts[position], position)
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
        private var mediaItemIndex = 0

        fun bindView(post: Post, position: Int) {
            binding.apply {
                if (post.type == FileType.IMAGE.type) {
                    imgPost.visibility = View.VISIBLE
                    videoPost.visibility = View.GONE
                    Glide.with(itemView.context).load(post.url).override(imageThumbnailSize)
                        .centerCrop().into(imgPost)
                } else {
                    videoPost.visibility = View.VISIBLE
                    imgPost.visibility = View.GONE
                    if (position != 0) {
                        VideoPlayer.player?.let { player ->
                            player.addMediaItem(MediaItem.fromUri(Uri.parse(post.url)))
                            mediaItemIndex = player.mediaItemCount - 1
                            VideoPlayer.postDetailVideos.add(mediaItemIndex)
                            player.prepare()
                        }
                    }
                }
            }
        }

        @androidx.annotation.OptIn(UnstableApi::class)
        fun addPlayer() {
            if (binding.videoPost.visibility == View.VISIBLE) {
                binding.videoPost.player = VideoPlayer.player
                if (VideoPlayer.player?.isPlaying == false) {
                    VideoPlayer.player?.seekTo(mediaItemIndex, 0)
                    VideoPlayer.player?.play()
                }
                binding.videoPost.hideController()
            } else {
                VideoPlayer.player?.pause()
            }
        }

        fun removePlayer() {
            if (binding.videoPost.visibility == View.VISIBLE) {
                binding.videoPost.player = null
            }
        }
    }
}
