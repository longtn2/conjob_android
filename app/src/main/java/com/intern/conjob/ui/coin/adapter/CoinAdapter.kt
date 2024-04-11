package com.intern.conjob.ui.coin.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.intern.conjob.arch.extensions.convertDpToPx
import com.intern.conjob.arch.extensions.onClick
import com.intern.conjob.arch.util.Constant.IMAGE_THUMBNAIL_SIZE
import com.intern.conjob.data.model.Coin
import com.intern.conjob.databinding.ItemCoinBinding

class CoinAdapter : RecyclerView.Adapter<CoinAdapter.ViewHolder>() {
    var coins: List<Coin> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    internal var onItemClicked: (() -> Unit) = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCoinBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(coins[position])
    }

    override fun getItemCount(): Int = coins.size

    inner class ViewHolder(private val binding: ItemCoinBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val imageThumbnailSize = itemView.context.convertDpToPx(IMAGE_THUMBNAIL_SIZE)

        init {
            binding.root.onClick {
                onItemClicked.invoke()
            }
        }

        fun onBind(coin: Coin) {
            Glide.with(itemView.context).load(coin.item.thumb)
                .override(imageThumbnailSize).fitCenter()
                .into(binding.imgAvatar)
            binding.tvName.text = coin.item.name
        }
    }
}
