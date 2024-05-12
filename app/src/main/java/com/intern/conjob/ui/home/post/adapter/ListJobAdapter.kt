package com.intern.conjob.ui.home.post.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.intern.conjob.R
import com.intern.conjob.data.model.Job
import com.intern.conjob.databinding.ItemJobBinding

class ListJobAdapter : RecyclerView.Adapter<ListJobAdapter.JobViewHolder>() {
    var jobs: List<Job> = listOf()
        @SuppressLint("NotifyDataSetChanged") set(value) {
            field = value
            notifyDataSetChanged()
        }

    internal var onDetailsClick: ((jobId: Long) -> Unit) = { _ ->  }

    internal var onItemClick: ((jobId: Long) -> Unit) = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder =
        JobViewHolder(
            ItemJobBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        holder.bindView(jobs[position])
    }

    override fun getItemCount(): Int = jobs.size

    inner class JobViewHolder(
        private val binding: ItemJobBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(job: Job) {
            binding.apply {
                tvTitle.text = job.title
                tvJobType.text = job.jobType
                tvApplicants.text = itemView.context.getString(R.string.job_applications, job.applicants.size, job.quantity)
                tvLocation.text = job.location
            }
            binding.imgDetails.setOnClickListener {
                onDetailsClick.invoke(job.id)
            }
            binding.cardViewJob.setOnClickListener {
                onItemClick.invoke(job.id)
            }
        }
    }
}