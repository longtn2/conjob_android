package com.intern.conjob.ui.home.matching.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.media3.common.MediaItem
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.convertDpToPx
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.arch.util.Constants.EXPAND_DOTS
import com.intern.conjob.arch.util.Constants.EXPAND_TEXT
import com.intern.conjob.arch.util.Constants.POST_LIMIT
import com.intern.conjob.arch.util.FileType
import com.intern.conjob.arch.util.PostOnClickListener
import com.intern.conjob.arch.util.VideoPlayer
import com.intern.conjob.arch.util.format
import com.intern.conjob.data.model.Post
import com.intern.conjob.databinding.ItemPostBinding
import com.intern.conjob.ui.widget.CustomClickableSpan

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {
    var posts: List<Post> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            val addSize = value.size - posts.size
            field = value
            if (posts.size <= POST_LIMIT) {
                notifyDataSetChanged()
            } else {
                notifyItemRangeInserted(value.size - POST_LIMIT, addSize)
            }
        }

    companion object {
        var clickListener: PostOnClickListener? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bindView(posts[position])
    }

    override fun getItemCount(): Int = posts.size

    class PostViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val imageThumbnailSize = itemView.context.convertDpToPx(Constants.IMAGE_THUMBNAIL_SIZE)

        fun bindView(post: Post) {
            binding.apply {
                if (post.type == FileType.IMAGE.type) {
                    imgView.visibility = View.VISIBLE
                    playerView.visibility = View.GONE
                    Glide.with(itemView.context).load(post.url)
                        .override(imageThumbnailSize)
                        .fitCenter()
                        .into(binding.imgView)
                } else {
                    playerView.visibility = View.VISIBLE
                    imgView.visibility = View.GONE
                    VideoPlayer.player?.addMediaItem(MediaItem.fromUri(Uri.parse(post.url)))
                    VideoPlayer.player?.prepare()
                }
                Glide.with(itemView.context).load(post.avatar)
                    .override(imageThumbnailSize)
                    .fitCenter()
                    .into(binding.imgAvatar)
                tvUserName.text = itemView.context.getString(R.string.item_matching_user_name, post.author)
                tvCaption.text = post.caption
                tvTotalInteract.text = post.likes.format()
                initTextSpan(post)
                initListener()
            }
        }

        private fun initTextSpan(post: Post) {
            binding.apply {
                tvCaption.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        tvCaption.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        tvCaption.text = if (tvCaption.lineCount >= tvCaption.maxLines) {
                            tvCaption.text.subSequence(0, tvCaption.layout.getLineEnd(tvCaption.maxLines - 1) - EXPAND_TEXT.length - EXPAND_DOTS.length - 1)
                                .toString() + EXPAND_DOTS + EXPAND_TEXT
                        } else {
                            tvCaption.text.subSequence(0, tvCaption.layout.getLineEnd(tvCaption.layout.lineCount - 1))
                                .toString() + EXPAND_TEXT
                        }

                        val textSpan = SpannableStringBuilder(tvCaption.text)
                        textSpan.setSpan(object : CustomClickableSpan(itemView.context.getColor(R.color.white)) {
                            override fun onClick(widget: View) {
                                clickListener?.onDetailClick(post)
                            }
                        }, textSpan.indexOf(EXPAND_TEXT), textSpan.indexOf(EXPAND_TEXT) + EXPAND_TEXT.length, 0)
                        tvCaption.movementMethod = LinkMovementMethod.getInstance()
                        tvCaption.text = textSpan
                    }
                })
            }
        }

        private fun initListener() {
            binding.apply {
                imgAvatar.setOnClickListener {
                    clickListener?.onAvatarClick()
                }
                imgBtnInteract.setOnClickListener {
                    clickListener?.onInteractClick()
                }
                imgBtnComment.setOnClickListener {
                    clickListener?.onCommentClick()
                }
                imgBtnShare.setOnClickListener {
                    clickListener?.onShareClick()
                }
            }
        }
    }

    fun setOnClickListener(l: PostOnClickListener) {
        clickListener = l
    }
}
