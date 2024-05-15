package com.intern.conjob.ui.home.matching.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.convertDpToPx
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants
import com.intern.conjob.arch.util.Constants.APPLY_BUTTON_PRESSED
import com.intern.conjob.arch.util.Constants.CLOSE_DETAILS_VIEW_KEY
import com.intern.conjob.arch.util.Constants.SKIP_BUTTON_PRESSED
import com.intern.conjob.arch.util.VideoPlayer
import com.intern.conjob.arch.util.format
import com.intern.conjob.databinding.FragmentPostDetailsBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.matching.PostDetailViewModel
import com.intern.conjob.ui.home.matching.adapter.PostFileAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.SimpleDateFormat
import java.util.Locale

class PostDetailFragment : BaseFragment(R.layout.fragment_post_details) {
    private val binding by viewBinding(FragmentPostDetailsBinding::bind)
    private val viewModel by viewModels<PostDetailViewModel>()
    private val args by navArgs<PostDetailFragmentArgs>()
    private var adapter: PostFileAdapter? = null

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListener()

        viewModel.posts.onEach {
            adapter?.posts = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.apply {
            adapter = PostFileAdapter()
            viewPagerPost.adapter = adapter

            viewModel.setFirstPost(args.post)
            args.post.job?.id?.let { getPosts(it) }

            TabLayoutMediator(tabLayoutPost, viewPagerPost, true) { tab, _ ->
                tab.icon = getDrawable(activity as MainActivity, R.drawable.custom_tab_selector)
            }.attach()
            tabLayoutPost.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val post = tab?.position?.let { adapter?.posts?.get(it) }
                    post?.createAt?.let {
                        tvPostDate.text = getString(
                            R.string.post_detail_create_date,
                            SimpleDateFormat(Constants.DATE_TIME_FORMAT, Locale.US).parse(it)
                                ?.let { it1 ->
                                    SimpleDateFormat(
                                        Constants.POST_VIEW_DATE_FORMAT,
                                        Locale.US
                                    ).format(it1)
                                } ?: "")
                    }
                    tvTotalInteract.text = post?.likes?.format()
                    tvCaption.text = post?.caption
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
                override fun onTabReselected(tab: TabLayout.Tab?) = Unit
            })
        }
    }

    private fun initListener() {
        binding.apply {
            imgBtnBackArrow.setOnClickListener {
                VideoPlayer.removePostDetailVideo()
                controller.previousBackStackEntry?.savedStateHandle?.set(CLOSE_DETAILS_VIEW_KEY, "")
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
                VideoPlayer.removePostDetailVideo()
                controller.previousBackStackEntry?.savedStateHandle?.set(
                    CLOSE_DETAILS_VIEW_KEY,
                    APPLY_BUTTON_PRESSED
                )
                controller.popBackStack()
            }

            btnSkip.setOnClickListener {
                VideoPlayer.removePostDetailVideo()
                controller.previousBackStackEntry?.savedStateHandle?.set(
                    CLOSE_DETAILS_VIEW_KEY,
                    SKIP_BUTTON_PRESSED
                )
                controller.popBackStack()
            }
        }
    }

    private fun initViews() {
        binding.apply {
            tvCaption.text = args.post.caption
            Glide.with(activity as MainActivity).load(args.post.avatar)
                .override((activity as MainActivity).convertDpToPx(Constants.IMAGE_THUMBNAIL_SIZE))
                .fitCenter().into(imgAvatar)
            args.post.createAt?.let {
                tvPostDate.text = getString(
                    R.string.post_detail_create_date,
                    SimpleDateFormat(Constants.DATE_TIME_FORMAT, Locale.US).parse(it)?.let { it1 ->
                        SimpleDateFormat(Constants.POST_VIEW_DATE_FORMAT, Locale.US).format(it1)
                    } ?: "")
            }
            tvUserName.text = args.post.author
            tvTotalInteract.text = args.post.likes.format()

            if (args.post.job != null) {
                tvJob.text = args.post.job?.title
                tvJobDescription.text = args.post.job?.description
                args.post.job?.expiredDay?.let {
                    tvTime.text =
                        SimpleDateFormat(Constants.DATE_TIME_FORMAT, Locale.US).parse(it)?.let { it1 ->
                            SimpleDateFormat(Constants.POST_VIEW_DATE_FORMAT, Locale.US).format(it1)
                        } ?: ""
                }
                tvLocation.text = args.post.job?.location
                tvBudget.text = args.post.job?.budget?.format() ?: ""
                tvQuantity.text = args.post.job?.quantity.toString()
            } else {
                tvJobTitle.visibility = View.GONE
                tvJobDescriptionTitle.visibility = View.GONE
                tvTimeTitle.visibility = View.GONE
                tvLocationTitle.visibility = View.GONE
                tvBudgetTitle.visibility = View.GONE
                tvQuantityTitle.visibility = View.GONE
            }
        }
    }

    private fun getPosts(jobId: Long) {
        viewModel.getPostsByJobID(jobId).launchIn(lifecycleScope)
    }
}
