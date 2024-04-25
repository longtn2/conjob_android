package com.intern.conjob.ui.home.matching.adapter

import android.annotation.SuppressLint
import android.net.Uri
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.BaseAdapter
import android.widget.TextView.BufferType
import androidx.annotation.OptIn
import androidx.media3.common.util.UnstableApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.convertDpToPx
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.arch.util.Constants.EXPAND_DOTS
import com.intern.conjob.arch.util.Constants.EXPAND_TEXT
import com.intern.conjob.arch.util.FileType
import com.intern.conjob.arch.util.PostOnClickListener
import com.intern.conjob.data.model.Post
import com.intern.conjob.databinding.ItemPostBinding
import com.intern.conjob.ui.widget.CustomClickableSpan


class PostAdapter : BaseAdapter() {
    var posts: List<Post> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    companion object {
        var clickListener: PostOnClickListener? = null
    }

    override fun getCount(): Int = posts.size

    override fun getItem(position: Int): Any? = null

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: PostViewHolder = if (convertView == null) {
            PostViewHolder(
                ItemPostBinding.inflate(
                    LayoutInflater.from(parent!!.context), parent, false
                )
            )
        } else {
            convertView.tag as PostViewHolder
        }
        holder.bindView(posts[position])
        holder.itemView.tag = holder
        return holder.itemView
    }

    class PostViewHolder(
        private val binding: ItemPostBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private val imageThumbnailSize = itemView.context.convertDpToPx(Constants.IMAGE_THUMBNAIL_SIZE)

        @OptIn(UnstableApi::class)
        fun bindView(post: Post) {
            binding.apply {
                if (post.type == FileType.IMAGE.type) {
                    imgView.visibility = View.VISIBLE
                    Glide.with(itemView.context).load(post.url)
                        .override(imageThumbnailSize)
                        .fitCenter()
                        .into(binding.imgView)
                } else {
                    videoView.visibility = View.VISIBLE
                    videoView.setVideoURI(Uri.parse(post.url))
                }
                tvUserName.text = itemView.context.getString(R.string.item_matching_user_name, post.author)
                tvCaption.text = post.caption
                initTextSpan()
                initListener()
            }
        }

        private fun initTextSpan() {
            binding.apply {
                tvCaption.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        tvCaption.viewTreeObserver.removeOnGlobalLayoutListener(this)

                        tvCaption.text = if (tvCaption.lineCount >= tvCaption.maxLines) {
                            tvCaption.text.subSequence(0, tvCaption.layout.getLineEnd(tvCaption.maxLines - 1) - EXPAND_TEXT.length - EXPAND_DOTS.length)
                                .toString() + EXPAND_DOTS + EXPAND_TEXT
                        } else {
                            tvCaption.text.subSequence(0, tvCaption.layout.getLineEnd(tvCaption.layout.lineCount - 1))
                                .toString() + EXPAND_TEXT
                        }

                        tvCaption.movementMethod = LinkMovementMethod.getInstance()

                        val textSpan = SpannableStringBuilder(tvCaption.text)
                        if (textSpan.contains(EXPAND_TEXT)) {
                            textSpan.setSpan(object : CustomClickableSpan(itemView.context.getColor(R.color.white)) {
                                override fun onClick(widget: View) {
                                    clickListener?.onDetailClick()
                                }
                            }, textSpan.indexOf(EXPAND_TEXT), textSpan.indexOf(EXPAND_TEXT) + EXPAND_TEXT.length, 0)
                        }
                        tvCaption.setText(textSpan, BufferType.SPANNABLE)
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
