package com.intern.conjob.ui.home.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intern.conjob.arch.util.ProfileTabItem
import com.intern.conjob.databinding.ItemProfileTabBinding

class ProfileTabAdapter: RecyclerView.Adapter<ProfileTabAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemProfileTabBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView()
    }

    override fun getItemCount(): Int = ProfileTabItem.entries.size

    class ViewHolder(
        binding: ItemProfileTabBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bindView() = Unit
    }
}
