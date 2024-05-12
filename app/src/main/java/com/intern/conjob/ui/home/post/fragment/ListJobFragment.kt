package com.intern.conjob.ui.home.post.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.databinding.FragmentListJobBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.post.ListJobViewModel
import com.intern.conjob.ui.home.post.adapter.ListJobAdapter

class ListJobFragment: BaseFragment(R.layout.fragment_list_job) {
    private val binding by viewBinding(FragmentListJobBinding::bind)
    private val viewModel by viewModels<ListJobViewModel>()
    private var adapter: ListJobAdapter? = null

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            adapter = ListJobAdapter()
            adapter?.jobs = viewModel.getTempData()

            recyclerViewJob.layoutManager = LinearLayoutManager(activity as MainActivity, LinearLayoutManager.VERTICAL, false)
            recyclerViewJob.adapter = adapter

            adapter?.let {
                it.onDetailsClick = { _ ->
                    Toast.makeText(
                        activity as MainActivity, R.string.toast_post_job_details, Toast.LENGTH_SHORT
                    ).show()
                }

                it.onItemClick = { jobId ->
                    controller.previousBackStackEntry?.savedStateHandle?.set(Constants.JOB_ID_KEY, jobId)
                    findNavController().popBackStack()
                }
            }

            cardViewCreateJob.setOnClickListener {
                findNavController().navigate(ListJobFragmentDirections.actionListJobFragmentToCreateJobFragment())
            }

            toolBar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }
}
