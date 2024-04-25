package com.intern.conjob.ui.home.matching.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.databinding.FragmentPostDetailsBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.matching.PostDetailViewModel
import com.intern.conjob.ui.home.matching.adapter.PostFileAdapter

class PostDetailFragment : BaseFragment(R.layout.fragment_post_details) {
    private val binding by viewBinding(FragmentPostDetailsBinding::bind)
    private val viewModel by viewModels<PostDetailViewModel>()
    private var adapter: PostFileAdapter? = null

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListener()
        initViews()
    }

    private fun initAdapter() {
        binding.apply {
            adapter = PostFileAdapter()
            adapter?.posts = viewModel.getTempData()
            viewPagerPost.adapter = adapter
            TabLayoutMediator(tabLayoutPost, viewPagerPost, false) { tab, _ ->
                tab.icon = getDrawable(activity as MainActivity, R.drawable.custom_tab_selector)
            }.attach()
        }
    }

    private fun initListener() {
        binding.apply {
            imgBtnBackArrow.setOnClickListener {
                controller.popBackStack()
            }

            imgBtnMore.setOnClickListener {
                Toast.makeText(
                    activity as MainActivity,
                    getString(R.string.toast_post_detail_more),
                    Toast.LENGTH_SHORT
                ).show()
            }

            imgBtnInteract.setOnClickListener {
                Toast.makeText(
                    activity as MainActivity,
                    getString(R.string.toast_matching_interact),
                    Toast.LENGTH_SHORT
                ).show()
            }

            imgBtnComment.setOnClickListener {
                Toast.makeText(
                    activity as MainActivity,
                    getString(R.string.toast_matching_comment),
                    Toast.LENGTH_SHORT
                ).show()
            }

            imgBtnShare.setOnClickListener {
                Toast.makeText(
                    activity as MainActivity,
                    getString(R.string.toast_matching_share),
                    Toast.LENGTH_SHORT
                ).show()
            }

            btnApply.setOnClickListener {
                Toast.makeText(
                    activity as MainActivity,
                    getString(R.string.toast_matching_accept),
                    Toast.LENGTH_SHORT
                ).show()
            }

            btnSkip.setOnClickListener {
                Toast.makeText(
                    activity as MainActivity,
                    getString(R.string.toast_matching_skip),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initViews() {
        binding.apply {

        }
    }
}
