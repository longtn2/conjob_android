package com.intern.conjob.ui.skill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.intern.conjob.arch.util.Skill
import com.intern.conjob.databinding.ItemSkillBinding

class SkillAdapter: RecyclerView.Adapter<SkillAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemSkillBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(Skill.values()[position].skill)
    }

    override fun getItemCount(): Int = Skill.values().size

    class ViewHolder(private val binding: ItemSkillBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindView(skill: String) {
            binding.checkBoxSkill.text = skill
        }
    }
}