package com.intern.conjob.ui.home.matching.fragment

import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.OptIn
import androidx.core.view.size
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import com.intern.conjob.R
import com.intern.conjob.arch.extensions.onError
import com.intern.conjob.arch.extensions.viewBinding
import com.intern.conjob.arch.util.Constants.APPLY_BUTTON_PRESSED
import com.intern.conjob.arch.util.Constants.BLUR_EFFECT_RADIUS
import com.intern.conjob.arch.util.Constants.CARD_STACK_VIEW_MAX_DEGREE
import com.intern.conjob.arch.util.Constants.CARD_STACK_VIEW_SWIPE_THRESHOLD
import com.intern.conjob.arch.util.Constants.CARD_STACK_VIEW_VISIBLE_COUNT
import com.intern.conjob.arch.util.Constants.CLOSE_DETAILS_VIEW_KEY
import com.intern.conjob.arch.util.Constants.SKIP_BUTTON_PRESSED
import com.intern.conjob.arch.util.ErrorMessage
import com.intern.conjob.arch.util.FileType
import com.intern.conjob.arch.util.PostOnClickListener
import com.intern.conjob.arch.util.VideoPlayer
import com.intern.conjob.data.model.Post
import com.intern.conjob.data.error.ErrorModel
import com.intern.conjob.databinding.FragmentMatchingBinding
import com.intern.conjob.databinding.ItemPostBinding
import com.intern.conjob.ui.MainActivity
import com.intern.conjob.ui.base.BaseFragment
import com.intern.conjob.ui.base.BaseViewModel
import com.intern.conjob.ui.home.HomeFragmentDirections
import com.intern.conjob.ui.home.matching.MatchingViewModel
import com.intern.conjob.ui.home.matching.adapter.PostAdapter
import com.intern.conjob.ui.onboarding.OnBoardingActivity
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.SwipeAnimationSetting
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.net.HttpURLConnection

class MatchingFragment : BaseFragment(R.layout.fragment_matching) {
    private val binding by viewBinding(FragmentMatchingBinding::bind)
    private val viewModel by viewModels<MatchingViewModel>()
    private var adapter: PostAdapter? = null
    private var blurView: View? = null
    private var currentPlayerView: PlayerView? = null
    private var cardLayoutManager: CardStackLayoutManager? = null

    companion object {
        fun newInstance() = MatchingFragment()
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initCardStackView()

        binding.imgBtnSearch.setOnClickListener {
            Toast.makeText(context, getString(R.string.toast_matching_search), Toast.LENGTH_SHORT)
                .show()
        }

        binding.btnRefreshPost.setOnClickListener {
            if (viewModel.isGetMorePosts()) {
                getMorePosts()
            } else {
                getPosts()
            }
        }

        viewModel.posts.onEach {
            adapter?.posts = it
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        controller.currentBackStackEntry?.savedStateHandle?.getLiveData<String>(
            CLOSE_DETAILS_VIEW_KEY
        )
            ?.observe(viewLifecycleOwner) {
                if (it == APPLY_BUTTON_PRESSED) {
                    cardLayoutManager?.setSwipeAnimationSetting(
                        SwipeAnimationSetting.Builder().setDirection(Direction.Right).build()
                    )
                    binding.cardStackView.swipe()
                } else if (it == SKIP_BUTTON_PRESSED) {
                    cardLayoutManager?.setSwipeAnimationSetting(
                        SwipeAnimationSetting.Builder().setDirection(Direction.Left).build()
                    )
                    binding.cardStackView.swipe()
                }
                currentPlayerView?.player = VideoPlayer.player
            }
    }

    override fun onPause() {
        super.onPause()
        currentPlayerView?.let {
            VideoPlayer.player?.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        currentPlayerView?.let {
            VideoPlayer.player?.play()
        }
    }

    private fun initAdapter() {
        binding.apply {
            adapter = PostAdapter()
            getPosts()
            adapter?.setOnClickListener(object : PostOnClickListener {
                override fun onDetailClick(post: Post) {
                    currentPlayerView?.player = null
                    controller.navigate(HomeFragmentDirections.actionHomeFragmentToPostDetailFragment(post))
                }

                override fun onAvatarClick() {
                    Toast.makeText(
                        context,
                        getString(R.string.toast_matching_profile),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onInteractClick() {
                    Toast.makeText(
                        context,
                        getString(R.string.toast_matching_interact),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onCommentClick() {
                    Toast.makeText(
                        context,
                        getString(R.string.toast_matching_comment),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onShareClick() {
                    Toast.makeText(
                        context,
                        getString(R.string.toast_matching_share),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun initCardStackView() {
        cardLayoutManager =
            CardStackLayoutManager(activity as MainActivity, object : CardStackListener {
                @OptIn(UnstableApi::class)
                override fun onCardDragging(direction: Direction?, ratio: Float) {
                    currentPlayerView?.let {
                        VideoPlayer.player?.pause()
                        currentPlayerView?.hideController()
                    }
                    renderBlurEffect(true)
                }

                override fun onCardSwiped(direction: Direction?) {
                    cardSwiped(direction)
                }

                override fun onCardRewound() = Unit

                override fun onCardCanceled() {
                    currentPlayerView?.let {
                        VideoPlayer.player?.play()
                    }
                    renderBlurEffect(false)
                }

                override fun onCardAppeared(view: View?, position: Int) {
                    view?.let { cardView ->
                        val cardViewBinding = ItemPostBinding.bind(cardView)
                        blurView = cardViewBinding.constraintLayout
                        adapter?.let {
                            if (it.posts[position].type == FileType.VIDEO.type) {
                                VideoPlayer.player?.play()
                                currentPlayerView = cardViewBinding.playerView
                                currentPlayerView?.player = VideoPlayer.player
                            }
                        }
                    }
                    binding.btnRefreshPost.visibility = View.GONE
                }

                override fun onCardDisappeared(view: View?, position: Int) {
                    currentPlayerView?.player = null
                    renderBlurEffect(false)
                    adapter?.let {
                        if (it.posts[position].type == FileType.VIDEO.type) {
                            VideoPlayer.player?.currentMediaItemIndex?.let { index ->
                                VideoPlayer.player?.removeMediaItem(index)
                            }
                            VideoPlayer.player?.seekTo(0)
                            currentPlayerView = null
                        }
                        it.notifyItemChanged(0)
                    }
                }
            })
        cardLayoutManager?.setVisibleCount(CARD_STACK_VIEW_VISIBLE_COUNT)
        cardLayoutManager?.setMaxDegree(CARD_STACK_VIEW_MAX_DEGREE)
        cardLayoutManager?.setSwipeThreshold(CARD_STACK_VIEW_SWIPE_THRESHOLD)
        cardLayoutManager?.setDirections(Direction.HORIZONTAL)
        binding.cardStackView.layoutManager = cardLayoutManager
        binding.cardStackView.adapter = adapter
    }

    private fun cardSwiped(direction: Direction?) {
        when (direction) {
            Direction.Right -> {
                Toast.makeText(
                    activity as MainActivity,
                    getString(R.string.toast_matching_accept),
                    Toast.LENGTH_SHORT
                ).show()
            }

            Direction.Left -> {
                Toast.makeText(
                    activity as MainActivity,
                    getString(R.string.toast_matching_skip),
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
        if (binding.cardStackView.size <= CARD_STACK_VIEW_VISIBLE_COUNT - 1) {
            getMorePosts()
        }
        showEmptyPost()
    }

    private fun renderBlurEffect(isShow: Boolean) {
        if (isShow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                blurView?.setRenderEffect(
                    RenderEffect.createBlurEffect(
                        BLUR_EFFECT_RADIUS, BLUR_EFFECT_RADIUS, Shader.TileMode.MIRROR
                    )
                )
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                blurView?.setRenderEffect(null)
            }
        }
    }

    private fun getPosts() {
        viewModel.getPosts().onStart {
            showLoadingPost()
        }.onCompletion {
            showEmptyPost()
        }.onError(
            commonAction = {
                handleGetPostError(it)
            },
            normalAction = {
                handleGetPostError(it)
            }
        ).launchIn(lifecycleScope)
    }

    private fun getMorePosts() {
        if (viewModel.canCallApiGetMorePosts()) {
            viewModel.getMorePosts().onStart {
                showLoadingPost()
            }.onCompletion {
                showEmptyPost()
            }.onError(
                commonAction = {
                    handleGetPostError(it)
                },
                normalAction = {
                    handleGetPostError(it)
                }
            ).launchIn(lifecycleScope)
        }
    }

    private fun showEmptyPost() {
        if (binding.cardStackView.size == 0 && !viewModel.isLoading) {
            binding.btnRefreshPost.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showLoadingPost() {
        binding.btnRefreshPost.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
        binding.tvEmpty.text = getString(R.string.matching_post_loading)
    }

    private fun handleGetPostError(errorModel: ErrorModel) {
        if (errorModel is ErrorModel.Http.ApiError) {
            when (errorModel.code) {
                HttpURLConnection.HTTP_UNAUTHORIZED.toString() -> {
                    (activity as MainActivity).startActivity(Intent(activity as MainActivity, OnBoardingActivity::class.java))
                    (activity as MainActivity).finish()
                }
                HttpURLConnection.HTTP_FORBIDDEN.toString() -> binding.tvEmpty.text = ErrorMessage.VERIFY_EMAIL_FORBIDDEN_403.message
                HttpURLConnection.HTTP_INTERNAL_ERROR.toString() -> binding.tvEmpty.text = ErrorMessage.SERVER_ERROR_500.message
                else -> {
                    binding.tvEmpty.text = getString(R.string.matching_post_empty)
                }
            }
        } else if (errorModel is ErrorModel.LocalError) {
            binding.tvEmpty.text = errorModel.errorMessage
        }
    }
}
